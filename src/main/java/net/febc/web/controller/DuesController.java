package net.febc.web.controller;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.web.BaseResponse;
import net.febc.web.dto.req.dues.ReqChgDto;
import net.febc.web.dto.req.dues.ReqDuesDto;
import net.febc.web.dto.req.dues.ReqInsertDto;
import net.febc.web.dto.req.dues.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.dues.ResDetailDto;
import net.febc.web.dto.res.dues.ResInsertDto;
import net.febc.web.dto.res.dues.ResDuesMonthDto;
import net.febc.web.service.impl.DuesServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dues")
public class DuesController {

    private final DuesServiceImpl service;

    @GetMapping("/view/insert/{memberId}")
    public String insert(@PathVariable Long memberId, Model model) {
        BaseResponse<ResInsertDto> result = service.getUserInfo(memberId);
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            model.addAttribute("error", result.getValidate());
            return "dues/insert";
        }
        // 최종 회비 납부
        model.addAttribute("info", result.getData());
        return "dues/insert";
    }

    @PostMapping("/proc")
    public String procInsert(@ModelAttribute ReqInsertDto dto, Model model) {
        BaseResponse<Long> result = service.insertDues(dto);
        if(result.getValidate() != null
                && !result.getValidate().isEmpty()) {
            BaseResponse<ResInsertDto> userInfoResult = service.getUserInfo(dto.getMemberId());
            ResInsertDto data = userInfoResult.getData();
            if (data != null) {
                data.setPayment(dto.getPayment());
                model.addAttribute("info", data);
            }
            model.addAttribute("errorMsg", result.getValidate().get(0).getMessage());

            return "dues/insert";
        }
        return "redirect:/dues/view/detail/" + result.getData();
    }

    @GetMapping("/view/detail/{duesId}")
    public String viewDetail(@PathVariable Long duesId, Model model,
                             HttpServletRequest request) {

        BaseResponse<ResDetailDto> result = service.duesDetail(duesId);
        if(result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            String referer = request.getHeader("Referer");
            if (referer != null) {
                return "redirect:" + referer; // 이전 페이지로 리다이렉트
            } else {
                return "redirect:/";
            }
        }

        model.addAttribute("info", result.getData());

        return "dues/chg";
    }

    @PostMapping("/proc/search")
    @ResponseBody
    public ResDuesMonthDto getDues(@RequestBody ReqDuesDto duesDto) {
        return service.getMemberByMonth(duesDto);
    }

    @PostMapping("/proc/chg")
    public String chgProc(@ModelAttribute ReqChgDto chgDto,
                          Model model, HttpServletRequest request) {
        BaseResponse<Long> result = service.chgDues(chgDto);
        if(result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            String referer = request.getHeader("Referer");
            if (referer != null) {
                return "redirect:" + referer; // 이전 페이지로 리다이렉트
            } else {
                return "redirect:/";
            }
        }
        return "redirect:/dues/view/detail/" + result.getData();
    }

    @GetMapping("/view/list")
    public String list(@ModelAttribute ReqListDto dto, Model model) {

        if (dto == null) {
            dto = new ReqListDto();
        }

        BaseResponse<BasePaginationDto> result = service.getList(dto);
        model.addAttribute(Constants.PAGE_CONTENTS, result.getData().getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, result.getData());

        return "dues/list";
    }

    @PostMapping("/proc/list")
    public String procList(@ModelAttribute ReqListDto dto, Model model) {
        BaseResponse<BasePaginationDto> result = service.getList(dto);

        model.addAttribute("searchData", dto.getSearchData());
        model.addAttribute("type", dto.getType());
        model.addAttribute("startDate", dto.getStartDate());
        model.addAttribute("endDate", dto.getEndDate());

        model.addAttribute(Constants.PAGE_CONTENTS, result.getData().getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, result.getData());

        return "dues/list";
    }
}