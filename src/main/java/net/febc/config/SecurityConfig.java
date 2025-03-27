package net.febc.config;

import net.febc.cmmn.filter.LoggerFilter;
import net.febc.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    private final FailureHandler failureHandler;
    private final SuccessHandler successHandler;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthorizationFilter authorizationFilter;
    private final LoggerFilter loggerFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 로그인 처리
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)));
        // 로그인 패스
        authenticationFilter.setFilterProcessesUrl("/login");
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);

        return http
                .csrf().disable()
                .logout()
                .logoutUrl("/logout")         // 로그 아웃(/admin/logout/)후 이동(/)후 세션 삭제
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                // 인증 대상외 패스
                .authorizeRequests().antMatchers(AuthorizationFilter.EXCLUDE_URL.toArray(new String[AuthorizationFilter.EXCLUDE_URL.size()])).permitAll()
                .and()
                // 그 외 항목 전부 인증 적용
                .authorizeRequests().anyRequest().authenticated()
                .and()
                // 세션 사용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 부정 접근시 에러
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(loggerFilter, BasicAuthenticationFilter.class)
                // 로그인 처리
                .addFilter(authenticationFilter)
                // 공통 전처리 (필터)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
