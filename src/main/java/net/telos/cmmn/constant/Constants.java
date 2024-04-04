package net.telos.cmmn.constant;

import java.util.List;

/**
 * 프로젝트내의 상수 설정
 */
public class Constants {
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
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYYMMDD_HAN = "yyyy년 MM월 dd일";
    public static final String DATE_FORMAT_YYMMDD_HAN = "yy년 MM월 dd일";
    public static final String DATE_FORMAT_YYYYMMDD_HHMM_HAN = "YYYY년 MM월 dd일 HH시 mm분";

    // 영문 대/소문자, 한글, 숫자 사용가능
    public static final String CHECK_PATTERN_STRING_ALL = "^(?! )[0-9a-zA-Zㄱ-ㅎ가-힣 ]*";
    public static final String CHECK_PATTERN_PASSWORD = "^[0-9a-zA-Z~!@#$%^&*()_+=]*";

    public static final Integer USER_MISSION_LOGIN = Integer.valueOf(1);
    public static final Integer USER_MISSION_PICK_UP = Integer.valueOf(2);
    public static final Integer USER_MISSION_MEET = Integer.valueOf(3);


    public static final String MEETROOM_TYPE_MEETING = "MEETING"; // 소회의실
    public static final String MEETROOM_TYPE_MIDDLE_CONFERENCE = "MIDDLE_CONFERENCE"; // 중회의실
    public static final String MEETROOM_TYPE_CONFERENCE = "CONFERENCE"; // 대회의실
    public static final String MEETROOM_TYPE_AUDITORIUM = "AUDITORIUM"; // 대강당


    // 계정 비밀번호 최소 글자수
    public static final Integer PASSWORD_MIN = 8;
    // 계정 비밀번호 최대 글자수
    public static final Integer PASSWORD_MAX = 16;
    // 협업공간 최소 글자수
    public static final Integer COLLABO_NAME_MIN = 2;
    // 협업공간 최대 글자수
    public static final Integer COLLABO_NAME_MAX = 16;
    // 협업공간 비밀번호 최소 글자수
    public static final Integer COLLABO_PASSWORD_MIN = 4;
    // 협업공간 비밀번호 최대 글자수
    public static final Integer COLLABO_PASSWORD_MAX = 16;
    public static final Integer MEETROOM_NAME_LENGTH = 20;
    public static final Integer MEETROOM_PASSWORD_MIN = 4;
    public static final Integer MEETROOM_PASSWORD_MAX = 16;
    public static final String FILE_SEPERATOR = ".";
    public static final String DIR_SEPERATOR = "/";
    public static final String UPLOAD_TYPE_MEET = "meet";
    public static final String UPLOAD_TYPE_COLLABO = "collabo";

    // 파일 업로드 위치
    public static final String UPLOAD_FILE_DIR = "uploads";

    public static final Integer COLLABO_SCHEDULE_BOOK_MAX_LIST = 10;

    public static final Integer OFFSET_DEFAULT = 20;
    public static final List<String> AVATAR_REQUIRED_LIST = List.of("H", "B", "F", "SK", "T", "U", "S");

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    public static final String DT_ROLE_VIEWER = "Viewer";

    public static final String UPLOAD_FILE_TYPE_MP4 = "MP4";
    public static final String UPLOAD_FILE_TYPE_PDF = "PDF";

    // 메타 로그인
    public static final Integer LOGIN_META = 1;

    // PM2.5
    public static final String AIR_QUALITY_PM25 = "PM2.5";
    // PM2.5
    public static final String AIR_QUALITY_PM1 = "PM1";
    // PM2.5
    public static final String AIR_QUALITY_PM10 = "PM10";

}
