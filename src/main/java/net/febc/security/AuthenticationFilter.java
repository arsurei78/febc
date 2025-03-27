package net.febc.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.febc.cmmn.web.BaseResponse;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.web.dto.req.ReqUserLoginDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * ID/패스워드를 받아 token에 저장
 */
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws UsernameNotFoundException {

        ReqUserLoginDto reqUserLoginDto = null;
        try {
            reqUserLoginDto = new ObjectMapper().readValue(request.getInputStream(), ReqUserLoginDto.class);
        } catch (IOException e) {
            throw new UsernameNotFoundException(null);
        }
        // 입력 정보가 존재하지 않으면 에러
        if (reqUserLoginDto == null ||
                StringUtils.isBlank(reqUserLoginDto.getUserId()) ||
                StringUtils.isBlank(reqUserLoginDto.getPassword())) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            try {
                new ObjectMapper().writeValue(response.getWriter(),
                        new BaseResponse<>(BaseResponseCode.ERROR, BaseResponseCode.LOGIN_INPUT));
                return null;
            } catch (IOException ex) {
                throw new UsernameNotFoundException(null);
            }
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(reqUserLoginDto.getUserId(), reqUserLoginDto.getPassword());
        return authenticationManager.authenticate(token);
    }
}
