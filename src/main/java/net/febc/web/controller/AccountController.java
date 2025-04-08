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

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @GetMapping("/view/insert")
    public String insert(Model model) {
        Map<String, String> expensensMap = Constants.expensensMap;
        model.addAttribute("expensensType", expensensMap);
        return "account/insert";
    }

    @PostMapping("/proc")
    public String insertProc(@ModelAttribute ReqInsertDto dto, Model model) {
        BaseResponse<ResDetailDto> insert = accountService.insert(dto);
        if (!insert.getValidate().isEmpty()) {
            model.addAttribute("type", dto.getType());
            model.addAttribute("expensensType", dto.getExpensensType());
            model.addAttribute("selectedDate", dto.getSelectedDate());
            model.addAttribute("amount", dto.getAmount());
            model.addAttribute("memo", dto.getMemo());
            model.addAttribute("errorMsg", insert.getValidate().get(0).getMessage());
            model.addAttribute("error", insert.getValidate().get(0).getField());
            return "account/insert";
        }
        return "redirect:/member/view/detail/"+insert.getData().getId();
    }

    @PostMapping("/proc/chg")
    public String chgProc(@ModelAttribute ReqChgDto chgDto, Model model) {
        BaseResponse<ResDetailDto> result = accountService.chgAccount(chgDto);
        if (!result.getValidate().isEmpty()) {
            return "redirect:/member/view/detail/"+chgDto.getId();
        }
        model.addAttribute("info", result.getData());
        return "account/detail";
    }

    @GetMapping("/view/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BaseResponse<ResDetailDto> result = accountService.detailAccount(id);
        if(!result.getValidate().isEmpty()) {
            model.addAttribute("errorMsg", result.getValidate().get(0).getMessage());
            model.addAttribute("error", result.getValidate().get(0).getField());
            return "account/view/list";
        }
        model.addAttribute("info", result.getData());
        return "account/detail";
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
}
