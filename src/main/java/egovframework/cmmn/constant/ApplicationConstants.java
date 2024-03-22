package egovframework.cmmn.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * application.xml의 정보를 수집
 *
 */
@Component
public class ApplicationConstants {

    public static String aesKey;


    // first 데이터 베이스 접속 정보
    public static String firstPlatform;
    public static String firstDdlAuto;
    public static String firstStrategy;

    // second 데이터 베이스 접속 정보
    public static String secondPlatform;
    public static String secondDdlAuto;
    public static String secondStrategy;

    @Value("${aes.key}")
    public void setAesKey(String value) {
        aesKey = value;
    }

    @Value("${spring.jpa.first.database-platform}")
    public void setFirstPlatform(String value) {
        firstPlatform = value;
    }

    @Value("${spring.jpa.first.hibernate.ddl-auto}")
    public void setFirstDdlAuto(String value) {
        firstDdlAuto = value;
    }
    @Value("${spring.jpa.first.hibernate.naming.physical-strategy}")
    public void setFirstStrategy(String value) {
        firstStrategy = value;
    }
    @Value("${spring.jpa.second.database-platform}")
    public void setSecondPlatform(String value) {
        secondPlatform = value;
    }
    @Value("${spring.jpa.second.hibernate.ddl-auto}")
    public void setSecondDdlAuto(String value) {
        secondDdlAuto = value;
    }
    @Value("${spring.jpa.second.hibernate.naming.physical-strategy}")
    public void setSecondStrategy(String value) {
        secondStrategy = value;
    }

}
