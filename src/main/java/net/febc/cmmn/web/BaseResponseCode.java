package net.febc.cmmn.web;

/**
 * 응답 코드 종류
 *
 */
public class BaseResponseCode {


    public static final String LOGIN_PASSWORD_CHECK = "LOGIN_PASSWORD_CHECK";
	public static final String LOGIN_FAIL = "LOGIN_FAIL";
    public static final String NOT_FOUND_USER = "NOT_FOUND_USER";

    private BaseResponseCode(){}

	public static final String SUCCESS = "SUCCESS"; // 성공
	public static final String ERROR = "ERROR"; // 에러
	public static final String VALIDATE_ERROR = "VALIDATE_ERROR"; // 유효성 체크 에러
	public static final String SERVER_ERROR = "SERVER_ERROR"; // 서버에러
	public static final String NOT_HAVE_ACCESS = "NOT_HAVE_ACCESS";   // 접근권한이 없습니다.
	public static final String ERROR_LOGIN = "ERROR_LOGIN"; // 재 접속이 필요한 에러
	public static final String LOGIN_FAIL_NO_AUTHORITY = "LOGIN_FAIL_NO_AUTHORITY"; // 로그인 권한 에러
	public static final String LOGIN_FAIL_PASSWORD_INCORRECT = "LOGIN_FAIL_PASSWORD_INCORRECT"; // 비밀번호가 일치하지 않는 경우 접속 불가
	public static final String UNAVAILABLE_CONNECT_TIME = "UNAVAILABLE_CONNECT_TIME"; // 접속 불가능 시간
	public static final String LOGIN_DUPLICATE = "LOGIN_DUPLICATE";
	public static final String LOGIN_INPUT = "LOGIN_INPUT";

	public static final String LOGIN_INVALID_USER = "LOGIN_INVALID_USER";
	public static final String JWT_AUTHORIZATION_HEADER = "JWT_AUTHORIZATION_HEADER";
	public static final String JWT_TOKEN_EXPIRED = "JWT_TOKEN_EXPIRED";
	public static final String JWT_AUTHORIZATION_ERROR = "JWT_AUTHORIZATION_ERROR";
	public static final String JWT_TOKEN_ERROR = "JWT_TOKEN_ERROR";
}

