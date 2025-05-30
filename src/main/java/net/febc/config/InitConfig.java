package net.febc.config;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.web.repository.first.entity.user.UserInfo;
import net.febc.web.repository.first.write.UserInfoWriteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;


/**
 * 서버 기동시 초기 설정 필요정보 처리 클래스
 * 
 */
@Configuration
@RequiredArgsConstructor
public class InitConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserInfoWriteRepository repository;

    /**
     * 서버 기동시 초기 설정 필요정보 처리
     * @return
     */
    @Bean
    @Transactional
    public CommandLineRunner initSuperAdminSetting(){
        return args -> {
            if(!repository.existsByUserId("admin")) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId("admin");
                userInfo.setPassword(passwordEncoder.encode("foreast25@"));
                userInfo.setUsername("관리자");
                userInfo.setAuthorities(new SimpleGrantedAuthority(Constants.ROLE_ADMIN));
                userInfo.setMobile("01042354957");
                repository.save(userInfo);

            }
        };
    }
}
