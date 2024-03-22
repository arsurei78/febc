package egovframework.security;

import egovframework.cmmn.utils.MessageUtils;
import egovframework.cmmn.web.BaseResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * 접근 권한이 없는 접근에 대한 에러
 */
@Component
public class CustomAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res,
                       AccessDeniedException ade) throws IOException {

        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setCharacterEncoding("UTF-8");
        String message = MessageUtils.getMessage(BaseResponseCode.LOGIN_FAIL_NO_AUTHORITY);
        String encoder = URLEncoder.encode(message, StandardCharsets.UTF_8);
        res.sendRedirect("/login?msg="+encoder);
    }
}
