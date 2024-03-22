package egovframework.cmmn;

import egovframework.web.dto.vo.UserInfoVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SessionManager {

    private SessionManager(){}

    // 로그인 유저 정보 키
    public static final String SESSION_USER_INFO = "LOGIN_USER_INFO";

    /**
     * 세션에 저장해둔 정보를 조회
     * @param request 요청Request
     * @param key 저장 키
     * @return object 저장한 정보
     */
    public static Object getSessionInfo(HttpServletRequest request, String key){
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }

    /**
     * 회원정보를 습득
     * @param request 요청Request
     * @return
     */
    public static UserInfoVo getUserInfo(HttpServletRequest request) {
        return (UserInfoVo)getSessionInfo(request, SESSION_USER_INFO);
    }

    /**
     * 인증 정보를 습득
     * @param request 요청Request
     * @return
     */
    public static Authentication getSecurityContext(HttpServletRequest request) {
        SecurityContextImpl securityContext = (SecurityContextImpl)getSessionInfo(request, HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext == null) {
            return null;
        }
        return securityContext.getAuthentication();
    }

    /**
     * 세션에 지정된 키의 데이터를 저장
     *
     * @param request 요청Request
     * @param key
     * @param value
     */
    public static HttpSession setSessionInfo(HttpServletRequest request, String key, Object value){
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        return session;
    }
}

