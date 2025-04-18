package net.febc.web.controller;

import lombok.RequiredArgsConstructor;
import net.febc.web.dto.req.ReqUserLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
