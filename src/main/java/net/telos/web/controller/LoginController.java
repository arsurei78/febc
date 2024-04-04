package net.telos.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.telos.web.dto.req.ReqUserLoginDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    @ApiOperation(tags = "0.로그인", value = "로그인", notes = "로그인 처리, 회원정보 / 토큰 정보를 반환" +
            "<p>로그인 체크</p>" +
            "<p>1. 입력 파라메터 입력 체크</p>" +
            "<p>2. 회원 존재 여부 체크</p>" +
            "<p>3. 패스워드 일치 여부 체크</p>" +
            "<p>4. 접근 가능유저(정상유저)</p>")
    @PostMapping("/login")
    public void login(@RequestBody ReqUserLoginDto reqUserLoginDto) {
        // 실제
    }
}
