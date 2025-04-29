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
	public static final String NOT_HAVE_ACCESS = "NOT_HAVE_ACCESS";   // 접근권한이 없습니다.
	public static final String LOGIN_INPUT = "LOGIN_INPUT";

	public static final String JWT_AUTHORIZATION_HEADER = "JWT_AUTHORIZATION_HEADER";
	public static final String JWT_TOKEN_EXPIRED = "JWT_TOKEN_EXPIRED";
	public static final String JWT_AUTHORIZATION_ERROR = "JWT_AUTHORIZATION_ERROR";
	public static final String JWT_TOKEN_ERROR = "JWT_TOKEN_ERROR";

	// 이름 체크
	public static final String MEMBER_NAME_NOT_INPUT = "MEMBER_NAME_NOT_INPUT";
	public static final String MEMBER_NAME_HANGUL = "MEMBER_NAME_HANGUL";
	public static final String MEMBER_IS_NOT_FOUND = "MEMBER_IS_NOT_FOUND";
	// 성별 체크
	public static final String MEMBER_SEX_NOT_INPUT = "MEMBER_SEX_NOT_INPUT";
	public static final String MEMBER_SEX_TYPE = "MEMBER_SEX_TYPE";
	// 기수 체크
	public static final String MEMBER_GENERATION_NOT_INPUT = "MEMBER_GENERATION_NOT_INPUT";
	// 회비 체크
	public static final String MEMBER_DUES_NOT_INPUT = "MEMBER_DUES_NOT_INPUT";

	// 지출 / 소비 정보를 찾을수 없음
	public static final String ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";
	// 존재하지 않는 타입
	public static final String ACCOUNT_TYPE_NOT_SUPPORT = "ACCOUNT_TYPE_NOT_SUPPORT";
	// 존재하지 소비 / 지출 않는 타입
	public static final String ACCOUNT_EXPENSENS_TYPE_NOT_SUPPORT = "ACCOUNT_EXPENSENS_TYPE_NOT_SUPPORT";
	// 금액이 적혀있지 않을 경우
	public static final String ACCOUNT_AMOUNT_NOT_INPUT = "ACCOUNT_AMOUNT_NOT_INPUT";
	// 날짜 선택이 잘못되었을떄
	public static final String ACCOUNT_EXPENSENS_SELECTED_DATE = "ACCOUNT_EXPENSENS_SELECTED_DATE";
	
	// 선택 날짜가 잘못된경우
	public static final String DUES_SELECTED_DATE = "DUES_SELECTED_DATE";
	// 납입금액 입금 안함
	public static final String DUES_DEPOSIT_NOT_INPUT = "DUES_DEPOSIT_NOT_INPUT";
	// 납입 정보를 찾을수 없음
	public static final String DUES_NOT_FOUND = "DUES_NOT_FOUND";
}

