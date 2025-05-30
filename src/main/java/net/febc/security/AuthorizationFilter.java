package net.febc.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.cmmn.web.BaseResponse;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.web.dto.req.user.UserAdapter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
            "/login", "/logout", "/token/**", "/", "/error",
            "/js/**", "/css/**", "/fonts/**", "/images/**",
            "/system/meta", "/system/init/**", "/v3/api-docs",
            "/favicon.ico");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 접속 URL
        String servletPath = request.getServletPath();
        if (EXCLUDE_URL.stream().anyMatch(exclude -> CommonUtils.match(exclude, servletPath))){

            filterChain.doFilter(request, response);
        } else {
            // 토큰 검증
            Object checkJwt = jwtUtils.checkCookieJwt(request);
            System.out.println("++++++++++++++++++++++++++++++++++");
            System.out.println("checkJwt : " + checkJwt);
            System.out.println("++++++++++++++++++++++++++++++++++");
            DecodedJWT decodedJWT = (DecodedJWT)checkJwt;
            String userId = decodedJWT.getSubject();

            // 조건에 의해 인증 정보를 갱신
            // 세션에서 회원ID를 습득
            UserAdapter userAdapter = (UserAdapter)userDetailsService.loadUserByUsername(userId);

            // 로그인 정보를 Security Context에 저장
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userAdapter, userAdapter.getPassword(), userAdapter.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            if (jwtUtils.isTokenExpiringSoon(request)) {
                // 처리후 쿠키 설정
                ResponseCookie cookie = jwtUtils.makeCookie(authenticationToken);
                // 쿠키 설정
                response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            }
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
        response.sendRedirect("/error");
    }

    /**
     * 접근 불가 시간을 체크
     * @return
     */
/*    private boolean checkAccessDeniedTime() {
        LocalDateTime now = LocalDateTime.now();
        // 접속 불가 시작 시간
        LocalDateTime startBlock = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0,0);
        // 접속 불가 종료 시간
        LocalDateTime endBlock = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 2, 0,0);
        // 00시에서 02시 사이에 접속 불가
        return now.isAfter(startBlock) && now.isBefore(endBlock);
    }*/
}
