package net.febc.security;

import net.febc.cmmn.constant.AppConstants;
import net.febc.web.dto.res.TokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private AppConstants appConstants;

    public CustomLoginFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils, AppConstants appConstants) {
        setFilterProcessesUrl("/login");
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.appConstants = appConstants;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("userId");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        return this.authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // 쿠키를 작성
        TokenDto tokenDto = jwtUtils.makeAccessToken(authResult.getName(), authResult.getAuthorities(), UUID.randomUUID().toString());
        // 만료시간
        long expire = appConstants.getMJWT_AT_EXP_TIME();
        ResponseCookie cookie = ResponseCookie.from("jwt", tokenDto.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofSeconds(expire))
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        response.sendRedirect("/account/view/list");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
