package net.telos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.telos.cmmn.web.BaseResponse;
import net.telos.cmmn.web.BaseResponseCode;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 로그인 실패후 처리
 */
@Component
public class FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        BaseResponse<String> baseResponse = new BaseResponse<>(BaseResponseCode.LOGIN_FAIL, BaseResponseCode.LOGIN_PASSWORD_CHECK);
        new ObjectMapper().writeValue(response.getWriter(), baseResponse);
    }
}
