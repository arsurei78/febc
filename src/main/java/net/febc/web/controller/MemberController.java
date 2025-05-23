package net.febc.web.controller;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.web.BaseResponse;
import net.febc.web.dto.req.member.ReqChgDto;
import net.febc.web.dto.req.member.ReqInsertDto;
import net.febc.web.dto.req.member.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.member.ResDetailDto;
import net.febc.web.service.impl.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;

    @GetMapping("/view/write")
    public String viewWrite(Model model) {
        return "member/insert";
    }

    @GetMapping("/view/detail/{memberId}")
    public String viewDetail(@PathVariable("memberId") Long memberId, Model model) {
        BaseResponse<ResDetailDto> result = memberService.detailMember(memberId);
        ResDetailDto data = result.getData();
        model.addAttribute("user", data);
        return "member/chg";
    }

    @GetMapping("/view/list")
    public String viewList(Model model) {
        ReqListDto reqListDto = new ReqListDto();
        reqListDto.setJoinState("Y");
        BaseResponse<BasePaginationDto> result = memberService.listMember(reqListDto);
        BasePaginationDto listData = result.getData();

        model.addAttribute("joinState", reqListDto.getJoinState());
        model.addAttribute("searchData", reqListDto.getSearchData());

        model.addAttribute(Constants.PAGE_CONTENTS, listData.getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, listData);
        return "member/list";
    }

    @PostMapping("/proc/list")
    public String listMember(@ModelAttribute ReqListDto reqListDto, Model model) {
        BaseResponse<BasePaginationDto> result = memberService.listMember(reqListDto);
        BasePaginationDto listData = result.getData();

        model.addAttribute("joinState", reqListDto.getJoinState());
        model.addAttribute("searchData", reqListDto.getSearchData());

        model.addAttribute(Constants.PAGE_CONTENTS, listData.getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, listData);
        return "member/list";
    }

    @PostMapping("/proc")
    public String writeMember(@ModelAttribute ReqInsertDto insertDto, Model model) {
        BaseResponse<ResDetailDto> result = memberService.writeMember(insertDto);
        // 유효성 체크 에러의 경우, 원 화면
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            model.addAttribute("user", insertDto.getName());
            model.addAttribute("gender", insertDto.getGender());
            model.addAttribute("generation", insertDto.getGeneration());
            model.addAttribute("dues", insertDto.getDues());
            model.addAttribute("errorMsg", result.getValidate().get(0).getMessage());
            return "/member/write";
        }
        return "redirect:/member/view/detail/"+result.getData().getId();
    }

    @PostMapping("/proc/chg")
    public String chgMember(@ModelAttribute ReqChgDto reqChgDto, Model model) {
        BaseResponse<ResDetailDto> result = memberService.chgMember(reqChgDto);
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            model.addAttribute("errorMsg", result.getValidate().get(0).getMessage());
        }
        return "redirect:/member/view/detail/"+reqChgDto.getId();
    }
    
    @GetMapping("/proc/state/{memberId}")
    public String chgState(@PathVariable("memberId") Long memberId, Model model) {
        BaseResponse<ResDetailDto> result = memberService.chgState(memberId);
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            model.addAttribute("error", result.getValidate().get(0));
        }
        return "redirect:/member/view/detail/"+memberId;
    }

    @DeleteMapping("/proc/{memberId}")
    public BaseResponse<ResDetailDto> deleteMember(@PathVariable("memberId") Long memberId) {
        return memberService.deleteMember(memberId);
    }
}
