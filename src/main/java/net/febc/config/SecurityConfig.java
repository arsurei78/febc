package net.febc.config;

import lombok.RequiredArgsConstructor;
import net.febc.security.AuthorizationFilter;
import net.febc.security.CustomLoginFilter;
import net.febc.security.JwtUtils;
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

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthorizationFilter authorizationFilter;
    private final JwtUtils jwtUtils;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // ✅ 중복 build 방지
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        CustomLoginFilter customLoginFilter = new CustomLoginFilter(authManager, jwtUtils);
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
                .addFilterBefore(authorizationFilter, CustomLoginFilter.class)       // 인증 검사 먼저
                .addFilterBefore(customLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"))
                .build();
    }
}
