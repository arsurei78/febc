package net.febc.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.cmmn.web.BaseResponse;
import net.febc.cmmn.web.BaseResponseCode;
import lombok.RequiredArgsConstructor;
import net.febc.web.dto.req.user.UserAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static net.febc.cmmn.constant.Constants.LOGIN_TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * filter 처리가 필요하면 적용
 */
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    // 요청 인증 무시 패스
    public static final List<String> EXCLUDE_URL = List.of(
            "/login", "/logout", "/account/join/**", "/token/**",
            "/uploads/**", "/test/**",
            "/system/meta", "/system/init/**", "/v3/api-docs",
            "/swagger-resources/**", "/swagger-ui.html", "/swagger-ui/**", "/favicon.ico");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 접속 URL
        String servletPath = request.getServletPath();
        if (EXCLUDE_URL.stream().anyMatch(exclude -> CommonUtils.match(exclude, servletPath))){
            filterChain.doFilter(request, response);
        } else {
            // 접속 가능 시간 체크(00시에서 02시 접속 금지)
            if ( checkAccessDeniedTime()) {
                errMapping(response, BaseResponseCode.UNAVAILABLE_CONNECT_TIME);
                return;
            }
            // 토큰 검증
            Object checkJwt = jwtUtils.checkJwt(request);
            // 검증후 토큰 복호화 데이터가 아닌경우, 에러
            if (!(checkJwt instanceof DecodedJWT)) {
                String errCode = (String)checkJwt;
                errMapping(response, errCode);
                return;
            }

            DecodedJWT decodedJWT = (DecodedJWT)checkJwt;
            String userId = decodedJWT.getSubject();

            // 조건에 의해 인증 정보를 갱신
            // 세션에서 회원ID를 습득
            UserAdapter userAdapter = (UserAdapter)userDetailsService.loadUserByUsername(userId);
            Claim claimToken = decodedJWT.getClaim(LOGIN_TOKEN);

            // 2중 로그인 체크
            // DB의 토큰 정보와 jwt의 토큰 정보가 일치하지 않는경우, 로그인 요구
            if (!userAdapter.getUserInfo().getApiToken().equals(claimToken.asString())) {
                errMapping(response, BaseResponseCode.LOGIN_DUPLICATE);
                return;
            }

            // 로그인 정보를 Security Context에 저장
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userAdapter, userAdapter.getPassword(), userAdapter.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }
    }

    /**
     * 필터에서 에러 발생시 반환정보작성후 반환
     * @param response HttpServletResponse
     * @param errCode 반환 에러 코드
     * @throws IOException
     */
    private void errMapping (HttpServletResponse response, String errCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        if (BaseResponseCode.LOGIN_DUPLICATE.equals(errCode)) {
            new ObjectMapper().writeValue(response.getWriter(), new BaseResponse<>(BaseResponseCode.ERROR_LOGIN, errCode));
        } else {
            new ObjectMapper().writeValue(response.getWriter(),
                    new BaseResponse<>(CommonUtils.validateResponse("", errCode)));
        }

    }

    /**
     * 접근 불가 시간을 체크
     * @return
     */
    private boolean checkAccessDeniedTime() {
        LocalDateTime now = LocalDateTime.now();
        // 접속 불가 시작 시간
        LocalDateTime startBlock = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0,0);
        // 접속 불가 종료 시간
        LocalDateTime endBlock = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 2, 0,0);
        // 00시에서 02시 사이에 접속 불가
        return now.isAfter(startBlock) && now.isBefore(endBlock);
    }
}
