package net.febc.web.controller;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.web.dto.req.closing.ReqInsertDto;
import net.febc.web.dto.req.closing.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.closing.ResDetailDto;
import net.febc.web.service.impl.ClosingServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;

@Controller
@RequestMapping("/closing")
@RequiredArgsConstructor
public class ClosingController {

    private final ClosingServiceImpl service;

    @GetMapping("/view/list")
    public String dashboard(@ModelAttribute ReqListDto dto, Model model) {

        if(dto == null ) {
            dto = new ReqListDto();
        }
        // 검색결과
        BasePaginationDto listData = service.getList(dto);
        // 검색정보
        model.addAttribute(Constants.PAGE_CONTENTS, listData.getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, listData);

        return "closing/list";
    }
    @PostMapping("/view/insert")
    public String detail(@ModelAttribute ReqInsertDto dto, Model model) {

        ResDetailDto resDetailDto = service.makeViewClosing(dto);
        model.addAttribute("info", resDetailDto);
        return "closing/insert";
    }

    @GetMapping("/view/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ResDetailDto resDetailDto = service.closingDetail(id);
        model.addAttribute("info", resDetailDto);
        return "closing/detail";
    }

    @PostMapping("/proc")
    public String insert(@ModelAttribute ReqInsertDto dto){
        // 결산 작성
        Long id = service.makeClosing(dto);

        return "redirect:/closing/view/detail/" + id;
    }

    @GetMapping("/proc/del/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteClosing(id);
        return "redirect:/closing/view/list";
    }
}
