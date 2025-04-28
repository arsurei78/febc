package net.febc.web.controller;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.utils.MessageUtils;
import net.febc.web.dto.req.ReqUserLoginDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/")
    public String viewLogin() {
        return "index";
    }

    @PostMapping("/login")
    public void login(@RequestBody ReqUserLoginDto reqUserLoginDto) {
        // 실제
    }
}
