package egovframework.security;

import egovframework.cmmn.utils.MessageUtils;
import egovframework.cmmn.web.BaseResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 로그인 실패후 처리
 */
@Component
@RequiredArgsConstructor
public class FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String message = "";
        // 로그인 체크
        if(exception instanceof UsernameNotFoundException ||
                exception instanceof BadCredentialsException) {
            message = MessageUtils.getMessage(BaseResponseCode.LOGIN_FAIL_PASSWORD_INCORRECT);
        }
        String encoder = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("/login?msg="+encoder);
    }
}
