package egovframework.security;


import egovframework.cmmn.SessionManager;
import egovframework.cmmn.utils.CommonUtils;
import egovframework.web.dto.vo.UserAdapter;
import egovframework.web.dto.vo.UserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * filter 처리가 필요하면 적용
 */
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    // 요청 인증 무시 패스
    public static final List<String> EXCLUDE_URL = List.of(
            "/", "/login", "/logout", "/user/login", "/uploads/**",
            "/error/**", "/api/getSenData",
            "/js/**", "/css/**", "/fonts/**", "/images/**", "/v3/api-docs",
            "/swagger-resources/**", "/swagger-ui.html", "/swagger-ui/**");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 진입 URL
        String servletPath = request.getServletPath();
        // 체크 대상외 URL의 경우, Security 체크처리를 하지 않음
        if (EXCLUDE_URL.stream().anyMatch(exclude -> CommonUtils.urlMatch(exclude, servletPath))){
            filterChain.doFilter(request, response);
        } else {
            // 세션 정보를 조회
            UserInfoVo user = SessionManager.getUserInfo(request);
            Authentication securityContextObject =  SessionManager.getSecurityContext(request);
            // 세션 정보 / Security Context가 존재하지 않으면 로그인 반환
            if (user == null || securityContextObject == null) {
                response.sendRedirect("/login");
                return;
            }
            // 조건에 의해 인증 정보를 갱신
            // 세션에서 회원ID를 습득
            UserAdapter userDetails = (UserAdapter)userDetailsService.loadUserByUsername(user.getUserId());
            // 2중 로그인 체크
            // DB의 토큰 정보와 세션 토큰 정보가 일치하지 않는경우, 로그인 요구
            if (!userDetails.getUserInfo().getAdminToken().equals(user.getToken())) {
                response.sendRedirect("/?duplicate=true");
                return;
            }

            // 로그인 체크
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }
    }
}
