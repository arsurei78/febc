package net.febc.web.controller;

import io.swagger.annotations.ApiOperation;
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
        BaseResponse<BasePaginationDto> result = memberService.listMember(new ReqListDto());
        BasePaginationDto listData = result.getData();

        model.addAttribute(Constants.PAGE_CONTENTS, listData.getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, listData);
        return "member/list";
    }

    @PostMapping("/proc/list")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 리스트 조회", notes = "멤버 리스트 조회")
    @ResponseBody
    public String listMember(@RequestBody ReqListDto reqListDto, Model model) {
        BaseResponse<BasePaginationDto> result = memberService.listMember(reqListDto);
        BasePaginationDto listData = result.getData();

        model.addAttribute(Constants.PAGE_CONTENTS, listData.getContents());
        model.addAttribute(Constants.PAGE_PAGINATION, listData);
        return "member/list";
    }

    @PostMapping("/proc")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 정보 작성", notes = "멤버 정보 작성")
    public String writeMember(@ModelAttribute ReqInsertDto insertDto, Model model) {
        BaseResponse<ResDetailDto> result = memberService.writeMember(insertDto);
        // 유효성 체크 에러의 경우, 원 화면
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            model.addAttribute("user", insertDto.getName());
            model.addAttribute("gender", insertDto.getGender());
            model.addAttribute("generation", insertDto.getGeneration());
            model.addAttribute("dues", insertDto.getDues());
            model.addAttribute("error", result.getValidate().get(0));
            return "/member/write";
        }
        return "redirect:/member/view/detail/"+result.getData().getId();
    }

    @PostMapping("/proc/chg")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 정보 수정", notes = "멤버 정보 수정")
    public String chgMember(@ModelAttribute ReqChgDto reqChgDto, Model model) {
        BaseResponse<ResDetailDto> result = memberService.chgMember(reqChgDto);
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            model.addAttribute("error", result.getValidate().get(0));
        }
        return "redirect:/member/view/detail/"+reqChgDto.getId();
    }
    
    @GetMapping("/proc/state/{memberId}")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 상태 변경", notes = "멤버 상태 변경")
    public String chgState(@PathVariable("memberId") Long memberId, Model model) {
        BaseResponse<ResDetailDto> result = memberService.chgState(memberId);
        if (result.getValidate() != null &&
                !result.getValidate().isEmpty()) {
            model.addAttribute("error", result.getValidate().get(0));
        }
        return "redirect:/member/view/detail/"+memberId;
    }

    @DeleteMapping("/proc/{memberId}")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 삭제", notes = "멤버 삭제")
    public BaseResponse<ResDetailDto> deleteMember(@PathVariable("memberId") Long memberId) {
        return memberService.deleteMember(memberId);
    }

/*    @GetMapping("/proc/{memberId}")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 조회", notes = "멤버 조회")
    public String detailMember(@PathVariable("memberId") Long memberId, Model model) {
        BaseResponse<ResDetailDto> result = memberService.detailMember(memberId);
        ResDetailDto data = result.getData();
        model.addAttribute("memberInfo", data);
        return "/member/detail";
    }*/
}
