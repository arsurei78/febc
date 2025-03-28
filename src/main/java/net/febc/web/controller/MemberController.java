package net.febc.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.febc.cmmn.web.BaseResponse;
import net.febc.web.dto.req.member.ReqChgDto;
import net.febc.web.dto.req.member.ReqInsertDto;
import net.febc.web.dto.req.member.ReqListDto;
import net.febc.web.dto.res.member.ResDetailDto;
import net.febc.web.dto.res.member.ResListDto;
import net.febc.web.service.impl.MemberServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.Path;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;

    @GetMapping("/view/write")
    public String viewWrite(Model model) {
        return "/member/write";
    }

    @GetMapping("/view/edit/{memberId}")
    public String viewEdit(@PathVariable("memberId") Long memberId, Model model) {
        BaseResponse<ResDetailDto> result = memberService.detailMember(memberId);
        ResDetailDto data = result.getData();
        model.addAttribute("memberInfo", data);
        return "/member/edit";
    }

    @GetMapping("/view/detail/{memberId}")
    public String viewDetail(@PathVariable("memberId") Long memberId, Model model) {
        BaseResponse<ResDetailDto> result = memberService.detailMember(memberId);
        ResDetailDto data = result.getData();
        model.addAttribute("memberInfo", data);
        return "/member/detail";
    }

    @GetMapping("/view/list")
    public String viewList(Model model) {
        return "/member/list";
    }

    @PostMapping("/list")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 리스트 조회", notes = "멤버 리스트 조회")
    @ResponseBody
    public BaseResponse<Page<ResListDto>> listMember(@RequestBody ReqListDto reqListDto) {
        return memberService.listMember(reqListDto);
    }

    @PostMapping
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 정보 작성", notes = "멤버 정보 작성")
    public String writeMember(@ModelAttribute ReqInsertDto insertDto, Model model) {
        BaseResponse<ResDetailDto> result = memberService.writeMember(insertDto);
        if (!result.getValidate().isEmpty()) {
            model.addAttribute("member", insertDto.getName());
            model.addAttribute("sex", insertDto.getSex());
            model.addAttribute("generation", insertDto.getGeneration());
            model.addAttribute("dues", insertDto.getDues());
            model.addAttribute("error", result.getValidate());
            return "/member/write";
        }
        return "redirect:/member/"+result.getData().getId();
    }

    @PutMapping
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 정보 수정", notes = "멤버 정보 수정")
    public BaseResponse<ResDetailDto> chgMember(@ModelAttribute ReqChgDto reqChgDto) {
        return memberService.chgMember(reqChgDto);
    }
    
    @GetMapping("/state/{memberId}")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 상태 변경", notes = "멤버 상태 변경")
    @ResponseBody
    public BaseResponse<ResDetailDto> chgState(@PathVariable("memberId") Long memberId) {
        return memberService.chgState(memberId);
    }

    @DeleteMapping("/{memberId}")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 삭제", notes = "멤버 삭제")
    @ResponseBody
    public BaseResponse<ResDetailDto> deleteMember(@PathVariable("memberId") Long memberId) {
        return memberService.deleteMember(memberId);
    }


    @GetMapping("/{memberId}")
    @ApiOperation(tags = "1.멤버 관리", value = "멤버 조회", notes = "멤버 조회")
    public String detailMember(@PathVariable("memberId") Long memberId, Model model) {
        BaseResponse<ResDetailDto> result = memberService.detailMember(memberId);
        ResDetailDto data = result.getData();
        model.addAttribute("memberInfo", data);
        return "/member/detail";
    }
}
