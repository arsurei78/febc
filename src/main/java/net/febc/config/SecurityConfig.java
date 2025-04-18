package net.febc.config;

import net.febc.cmmn.constant.AppConstants;
import net.febc.cmmn.filter.LoggerFilter;
import net.febc.security.*;
import lombok.RequiredArgsConstructor;
import net.febc.web.service.impl.UserLoginServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthorizationFilter authorizationFilter;
    private final JwtUtils jwtUtils;
    private final AppConstants appConstants;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // ✅ 중복 build 방지
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        CustomLoginFilter customLoginFilter = new CustomLoginFilter(authManager, jwtUtils, appConstants);
        customLoginFilter.setAuthenticationManager(authManager);

        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AuthorizationFilter.EXCLUDE_URL.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"))
                .build();
    }
}
