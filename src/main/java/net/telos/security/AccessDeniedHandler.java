package net.telos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.telos.cmmn.web.BaseResponse;
import net.telos.cmmn.web.BaseResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * 접근 권한이 없는 접근에 대한 에러
 */
@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res,
                       AccessDeniedException ade) throws IOException, ServletException {
        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType(APPLICATION_JSON_VALUE);
        res.setCharacterEncoding("utf-8");
        new ObjectMapper().writeValue(res.getWriter(),
                new BaseResponse<>(BaseResponseCode.ERROR, BaseResponseCode.NOT_HAVE_ACCESS));
    }
}
