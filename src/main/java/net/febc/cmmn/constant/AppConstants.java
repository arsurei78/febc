package net.febc.cmmn.constant;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * application.xml의 정보를 수집
 *
 */
@Component
@Getter
public class AppConstants {

    @Value("${aes.key}")
    private String aesKey;

    // first 데이터 베이스 접속 정보
    @Value("${spring.jpa.first.database-platform}")
    private String firstPlatform;
    @Value("${spring.jpa.first.hibernate.ddl-auto}")
    private String firstDdlAuto;
    @Value("${spring.jpa.first.hibernate.naming.physical-strategy}")
    private String firstStrategy;
    @PostConstruct
    protected void init() {
        mSECRET_KEY = Keys.hmacShaKeyFor(mJWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 비밀키 (API)
    @Value("${jwt.secret-key:Qiws8sD4mU7CSKhUX6q82t69KEkp7K9ff2QCu4k9gDJNgFUJ}")
    private String mJWT_SECRET;
    private SecretKey mSECRET_KEY;
    // Access-token 토큰 만료시간
    @Value("${jwt.access-expire}")
    private int mJWT_AT_EXP_TIME;
    // refresh-token 만료시간
    @Value("${jwt.refresh-expire}")
    private int mJWT_RT_EXP_TIME;


}
