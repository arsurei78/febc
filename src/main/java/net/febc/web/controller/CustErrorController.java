package net.febc.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,
                              HttpServletResponse response,
                              Model model) {
        // 에러 상태 코드 가져오기
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String msg = (String)request.getAttribute("error");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("code", statusCode);

            // 에러 코드별 메시지 추가 (선택)
            switch (statusCode) {
                case 404:
                    model.addAttribute("message", "페이지를 찾을 수 없습니다.");
                    break;
                case 500:
                    if(StringUtils.isNotEmpty(msg)) {
                        model.addAttribute("message", msg);
                    } else {
                        model.addAttribute("message", "서버 내부 오류입니다.");
                    }
                    ResponseCookie deleteOld = ResponseCookie.from("jwt", "")
                            .path("/")
                            .maxAge(0)
                            .build();
                    response.addHeader(HttpHeaders.SET_COOKIE, deleteOld.toString());
                    break;
                default:
                    model.addAttribute("message", "오류가 발생했습니다.");
            }
        } else {
            if(StringUtils.isNotEmpty(msg)) {
                model.addAttribute("message", msg);
            } else {
                model.addAttribute("message", "서버 내부 오류입니다.");
            }
        }

        return "error/error"; // templates/error/customError.html 로 이동
    }

}
