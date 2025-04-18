package net.febc.web.controller;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.web.BaseResponse;
import net.febc.web.dto.req.dues.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.service.impl.DuesServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dues")
public class DuesController {

    private final DuesServiceImpl service;

    @GetMapping("/view/list")
    public String list(@ModelAttribute ReqListDto dto, Model model) {
        if (dto == null) {
            dto = new ReqListDto();
        }

        BaseResponse<BasePaginationDto> result = service.getList(dto);

        BasePaginationDto listData = result.getData();
        model.addAttribute(Constants.PAGE_CONTENTS, listData.getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, listData);

        return "account/list";
    }
}