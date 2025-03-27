package net.febc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;


/**
 * 서버 기동시 초기 설정 필요정보 처리 클래스
 * 
 */
@Configuration
@RequiredArgsConstructor
public class InitConfig {

    /**
     * 서버 기동시 초기 설정 필요정보 처리
     * @return
     */
    @Bean
    @Transactional
    public CommandLineRunner initSuperAdminSetting(){
        return args -> {
            // 초기 처리
        };
    }
}
