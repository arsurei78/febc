package net.febc.web.controller;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.web.BaseResponse;
import net.febc.web.dto.req.account.ReqChgDto;
import net.febc.web.dto.req.account.ReqInsertDto;
import net.febc.web.dto.req.account.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.account.ResDetailDto;
import net.febc.web.service.impl.AccountServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @GetMapping("/view/insert")
    public String insert(Model model) {
        model.addAttribute("selectedDate", "");
        model.addAttribute("amount", "");
        model.addAttribute("memo", "");
        model.addAttribute("expensensMap", Constants.expensensMap);
        return "account/insert";
    }

    @PostMapping("/proc")
    public String insertProc(@ModelAttribute ReqInsertDto dto, Model model) {
        BaseResponse<ResDetailDto> insert = accountService.insert(dto);
        if (insert.getValidate() != null &&
                !insert.getValidate().isEmpty()) {
            model.addAttribute("expensensMap", Constants.expensensMap);
            model.addAttribute("type", dto.getType());
            model.addAttribute("expensensType", dto.getExpensensType());
            model.addAttribute("selectedDate", dto.getSelectedDate());
            model.addAttribute("amount", dto.getAmount());
            model.addAttribute("memo", dto.getMemo());
            model.addAttribute("errorMsg", insert.getValidate().get(0).getMessage());
            return "account/insert";
        }
        return "redirect:/account/view/detail/"+insert.getData().getId();
    }

    @PostMapping("/proc/chg")
    public String chgProc(@ModelAttribute ReqChgDto chgDto, Model model) {
        BaseResponse<ResDetailDto> result = accountService.chgAccount(chgDto);
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            return "redirect:/account/view/detail/"+chgDto.getId() + "?error=" + result.getValidate().get(0).getMessage();
        }
        model.addAttribute("info", result.getData());
        model.addAttribute("expensensMap", Constants.expensensMap);
        return "account/chg";
    }

    @GetMapping("/view/detail/{id}")
    public String detail(@PathVariable("id") Long id,
                         HttpServletRequest request, Model model) {
        BaseResponse<ResDetailDto> result = accountService.detailAccount(id);
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            model.addAttribute("errorMsg", result.getValidate().get(0).getMessage());
            return "account/view/list";
        }

        model.addAttribute("errorMsg", request.getAttribute("error"));
        model.addAttribute("expensensMap", Constants.expensensMap);
        model.addAttribute("info", result.getData());
        return "account/chg";
    }

    @GetMapping("/view/list")
    public String list(@ModelAttribute ReqListDto dto, Model model) {
        if (dto == null) {
            dto = new ReqListDto();
        }
        BaseResponse<BasePaginationDto> result = accountService.getList(dto);
        BasePaginationDto listData = result.getData();
        model.addAttribute(Constants.PAGE_CONTENTS, listData.getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, listData);

        return "account/list";
    }

    @PostMapping("/proc/list")
    public String procList(@ModelAttribute ReqListDto dto, Model model) {
        BaseResponse<BasePaginationDto> result = accountService.getList(dto);
        BasePaginationDto listData = result.getData();

        model.addAttribute("searchData", dto.getSearchData());
        model.addAttribute("type", dto.getType());
        model.addAttribute("startDate", dto.getStartDate());
        model.addAttribute("endDate", dto.getEndDate());

        model.addAttribute(Constants.PAGE_CONTENTS, listData.getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, listData);

        return "account/list";
    }
}
