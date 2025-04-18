package net.febc.web.service.impl;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.web.BaseResponse;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.cmmn.web.ValidateErrorResponse;
import net.febc.web.dto.req.member.ReqChgDto;
import net.febc.web.dto.req.member.ReqInsertDto;
import net.febc.web.dto.req.member.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.member.ResDetailDto;
import net.febc.web.dto.res.member.ResListDto;
import net.febc.web.repository.first.entity.member.MemberInfo;
import net.febc.web.repository.first.impl.MemberInfoRepositoryImpl;
import net.febc.web.repository.first.write.MemberInfoRepository;
import net.febc.web.service.impl.validate.MemberValidate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl {

    private final MemberInfoRepository memberInfoRepository;
    private final MemberInfoRepositoryImpl memberInfoRepositoryImpl;
    private final MemberValidate memberValidate;

    /**
     * 멤버 작성
     * @return
     */
    @Transactional
    public BaseResponse<ResDetailDto> writeMember(ReqInsertDto insertDto) {
        List<ValidateErrorResponse> validate = memberValidate.checkInsert(insertDto);
        if (!validate.isEmpty()) {
            return new BaseResponse<>(validate);
        }
        // 멤버 정보
        MemberInfo memberInfo = insertDto.toEntity();
        // 저장
        memberInfoRepository.save(memberInfo);

        return new BaseResponse<>(new ResDetailDto(memberInfo));
    }

    /**
     * 멤버 정보 수정
     * @return
     */
    @Transactional
    public BaseResponse<ResDetailDto> chgMember(ReqChgDto reqChgDto) {
        List<ValidateErrorResponse> validate = memberValidate.checkChg(reqChgDto);
        if (!validate.isEmpty()) {
            return new BaseResponse<>(validate);
        }
        // 멤버 정보 조회
        MemberInfo memberInfo = memberInfoRepository.findById(reqChgDto.getId()).orElse(null);
        memberInfo.setGeneration(reqChgDto.getGeneration());
        memberInfo.setDues(reqChgDto.getDues());
        memberInfo.setGender(reqChgDto.getGender());
        return new BaseResponse<>(new ResDetailDto(memberInfo));
    }

    /**
     * 상태 정보 변경(입단 <-> 퇴단)
     * @param memberId
     * @return
     */
    @Transactional
    public BaseResponse<ResDetailDto> chgState(Long memberId) {
        List<ValidateErrorResponse> validate = memberValidate.checkState(memberId);
        if (!validate.isEmpty()) {
            return new BaseResponse<>(validate);
        }
        // 멤버 정보 조회
        MemberInfo memberInfo = memberInfoRepository.findById(memberId).orElse(null);

        if(memberInfo.getState()) {
            memberInfo.setState(Boolean.FALSE);
        } else {
            memberInfo.setState(Boolean.TRUE);
        }

        return new BaseResponse<>(new ResDetailDto(memberInfo));
    }

    /**
     * 멤버 정보 퇴단
     * @param memberId
     * @return
     */
    @Transactional
    public BaseResponse<ResDetailDto> deleteMember(Long memberId) {

        List<ValidateErrorResponse> validate = memberValidate.checkDelete(memberId);
        if(!validate.isEmpty()) {
            return new BaseResponse<>(validate);
        }
        // 멤버 삭제
        memberInfoRepository.deleteById(memberId);

        return new BaseResponse<>();
    }

    /**
     * 멤버 리스트
     * @return
     */
    public BaseResponse<BasePaginationDto> listMember(ReqListDto reqListDto) {
        // 전달받은 값이 없으면 새로 생ㅅ어
        if (reqListDto == null) {
            reqListDto = new ReqListDto();
        }
        Page<ResListDto> memberInfoList = memberInfoRepositoryImpl.getMemberInfoList(reqListDto);
        // 멤버 리스트 반환
        return new BaseResponse<>(new BasePaginationDto<>(memberInfoList, reqListDto.getOffset(), Constants.PAGE_BLOCK_SIZE));
    }

    /**
     * 멤버 정보 상세
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public BaseResponse<ResDetailDto> detailMember(Long memberId) {
        // 멤버 정보 조회
        MemberInfo memberInfo = memberInfoRepository.findById(memberId).orElse(null);
        // 멤버 정보가 없는 경우,
        if(memberInfo == null) {
            return new BaseResponse<>(List.of(new ValidateErrorResponse("member", BaseResponseCode.MEMBER_IS_NOT_FOUND)));
        }
        return new BaseResponse<>(new ResDetailDto(memberInfo));
    }
}
