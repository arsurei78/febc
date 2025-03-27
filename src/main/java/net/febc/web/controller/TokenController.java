package net.febc.web.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.febc.cmmn.web.BaseResponse;
import net.febc.web.dto.res.TokenDto;
import net.febc.web.service.TokenService;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Token요청
 * Refresh Token, Access Token을 요청
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    @Qualifier("TokenService")
    private final TokenService tokenService;

    @GetMapping("/access")
    @ApiOperation(tags = "0.로그인", value = "토큰 재발행", notes = "refresh-Token 정보를 가지고 access-Token을 작성하여 반환한다.")
    public BaseResponse<List<TokenDto>> accessToken(HttpServletRequest request) throws EgovBizException {
        List<TokenDto> accessToken = tokenService.makeToken(request);
        return new BaseResponse<>(accessToken);
    }
}

