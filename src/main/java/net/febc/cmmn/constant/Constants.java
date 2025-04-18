package net.febc.cmmn.constant;

import net.febc.web.repository.comm.ExpensensEnum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 프로젝트내의 상수 설정
 */
public class Constants {

    // 페이징 블록 사이즈
    public static final Integer PAGE_BLOCK_SIZE = 10;

    public static final String PAGE_CONTENTS = "contents";
    public static final String PAGE_PAGINATION = "pagination";

    private Constants(){}

    // 액세스 토큰
    public static final String AT_HEADER = "access_token";
    // 리프레쉬 토큰
    public static final String RT_HEADER = "refresh_token";

    // 인증 헤더 정보의 접두사
    public static final String TOKEN_HEADER_PREFIX = "Bearer ";

    // 로그인 토큰 정보
    public static final String LOGIN_TOKEN = "token";

    // 날짜 포멧
    public static final String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYYMMDD_HAN = "yyyy년 MM월 dd일";
    public static final String DATE_FORMAT_YYMMDD_HAN = "yy년 MM월 dd일";
    public static final String DATE_FORMAT_YYYYMMDD_HHMM_HAN = "YYYY년 MM월 dd일 HH시 mm분";
    public static final String DATE_FORMAT_YYYYMM = "yy년 MM월";

    // 영문 대/소문자, 한글, 숫자 사용가능
    public static final String CHECK_PATTERN_HANGUL_ALL = "^[가-힣]{2,6}$";
    public static final String CHECK_PATTERN_STRING_ALL = "^(?! )[0-9a-zA-Zㄱ-ㅎ가-힣 ]*";
    public static final String CHECK_PATTERN_PASSWORD = "^[0-9a-zA-Z~!@#$%^&*()_+=]*";


    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer DEFAULT_BLOCK_SIZE = 10;


    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    // 지출/수입 메뉴 MAP
    public static final Map<String, String> expensensMap = new LinkedHashMap<>();

    static {
        expensensMap.put(ExpensensEnum.TS.toString(), "교사 사례비");
        expensensMap.put(ExpensensEnum.LOT.toString(), "지방세");
        expensensMap.put(ExpensensEnum.INT.toString(), "소득세");
        expensensMap.put(ExpensensEnum.GIFT.toString(), "선물");
        expensensMap.put(ExpensensEnum.SNACK.toString(), "간식비");
        expensensMap.put(ExpensensEnum.SHEET.toString(), "악보제본비");
        expensensMap.put(ExpensensEnum.ORTHER.toString(), "기타");
    }
}
