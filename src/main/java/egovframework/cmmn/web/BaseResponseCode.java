package egovframework.cmmn.web;

/**
 * 응답 코드 종류
 *
 */
public class BaseResponseCode {

	private BaseResponseCode(){}

	public static final String SUCCESS = "SUCCESS"; // 성공
	public static final String ERROR = "ERROR"; // 에러
	public static final String VALIDATE_ERROR = "VALIDATE_ERROR"; // 유효성 체크 에러
	public static final String SERVER_ERROR = "SERVER_ERROR"; // 서버에러
	public static final String ERROR_LOGIN = "ERROR_LOGIN"; // 재 접속이 필요한 에러
	public static final String LOGIN_FAIL_NO_AUTHORITY = "LOGIN_FAIL_NO_AUTHORITY"; // 로그인 권한 에러
	public static final String LOGIN_FAIL_PASSWORD_INCORRECT = "LOGIN_FAIL_PASSWORD_INCORRECT"; // 비밀번호가 일치하지 않는 경우 접속 불가
}

