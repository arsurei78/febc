package net.febc.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.febc.cmmn.utils.Aes;
import net.febc.cmmn.web.BaseResponse;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.web.dto.req.user.UserAdapter;
import net.febc.web.dto.res.TokenDto;
import net.febc.web.dto.res.login.ResLoginInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 인증성공후 처리
 */
@Component
@RequiredArgsConstructor
public class SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        UserAdapter userAdapter = (UserAdapter) authentication.getPrincipal();
        String token = UUID.randomUUID().toString();
        // Access-Token
        TokenDto accessDto = jwtUtils.makeAccessToken(userAdapter.getUsername(), userAdapter.getAuthorities(), token);
        // refresh-token
        TokenDto refreshDto = jwtUtils.makeRefreshToken(userAdapter.getUsername());
        // 토큰 정보(로그인 토큰정보, Refresh토큰 정보)를 저장
        //userService.loginUpdate(request, userAdapter.getUsername(), token, refreshDto.getToken());

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        ResLoginInfoDto userInfo = new ResLoginInfoDto();
        userInfo.setAccessDto(accessDto);
        userInfo.setRefreshDto(refreshDto);
        userInfo.setUsername(Aes.decryption(userAdapter.getUserInfo().getUsername()));
        userInfo.setUserId(userAdapter.getUserInfo().getUserId());

        new ObjectMapper().writeValue(response.getWriter(),
                new BaseResponse<>(BaseResponseCode.SUCCESS, userInfo));

    }
}
