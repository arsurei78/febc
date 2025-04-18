package net.febc.web.service.impl.validate;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.cmmn.web.ValidateErrorResponse;
import net.febc.web.dto.req.member.ReqChgDto;
import net.febc.web.dto.req.member.ReqInsertDto;
import net.febc.web.repository.first.write.MemberInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberValidate {

    private final MemberInfoRepository memberInfoRepository;

    private final String GENDER_MALE = "M";
    private final String GENDER_FEMALE = "F";
    /**
     * 멤버 가입 유효성 체크
     * @param insertDto
     * @return
     */
    public List<ValidateErrorResponse> checkInsert(ReqInsertDto insertDto) {

        // 입력 체크
        if (StringUtils.isBlank(insertDto.getName())) {
            return List.of(new ValidateErrorResponse("name", BaseResponseCode.MEMBER_NAME_NOT_INPUT));
        } else if(!insertDto.getName().matches(Constants.CHECK_PATTERN_HANGUL_ALL)) {
            // 한글 체크
            return List.of(new ValidateErrorResponse("name", BaseResponseCode.MEMBER_NAME_HANGUL));
        }
        // 입력체크
        if (StringUtils.isBlank(insertDto.getGender())) {
            return List.of(new ValidateErrorResponse("sex", BaseResponseCode.MEMBER_SEX_NOT_INPUT));
        } else if (!List.of(GENDER_MALE, GENDER_FEMALE).contains(insertDto.getGender())) {
            // 성별 확인
            return List.of(new ValidateErrorResponse("sex", BaseResponseCode.MEMBER_SEX_TYPE));
        }
        // 입력체크
        if (insertDto.getGeneration() == null) {
            return List.of(new ValidateErrorResponse("dues", BaseResponseCode.MEMBER_GENERATION_NOT_INPUT));
        }
        // 입력체크
        if (insertDto.getDues() == null) {
            return List.of(new ValidateErrorResponse("dues", BaseResponseCode.MEMBER_DUES_NOT_INPUT));
        }

        return List.of();
    }

    /**
     * 멤버 변경 유효성 체크
     * @param reqChgDto
     * @return
     */
    public List<ValidateErrorResponse> checkChg(ReqChgDto reqChgDto) {

        // 멤버를 못찾는 경우
        if (!memberInfoRepository.existsById(reqChgDto.getId())) {
            return List.of(new ValidateErrorResponse("memberId", BaseResponseCode.MEMBER_IS_NOT_FOUND));
        }

        // 입력체크
        if (reqChgDto.getGeneration() == null) {
            return List.of(new ValidateErrorResponse("dues", BaseResponseCode.MEMBER_GENERATION_NOT_INPUT));
        }
        // 입력체크
        if (reqChgDto.getDues() == null) {
            return List.of(new ValidateErrorResponse("dues", BaseResponseCode.MEMBER_DUES_NOT_INPUT));
        }

        return List.of();
    }

    /**
     * 퇴단 멤버 유효성 체크
     * @param memberId
     */
    public List<ValidateErrorResponse> checkDelete(Long memberId) {

        // 멤버를 못찾는 경우
        if (!memberInfoRepository.existsByIdAndState(memberId, Boolean.TRUE)) {
            return List.of(new ValidateErrorResponse("memberId", BaseResponseCode.MEMBER_IS_NOT_FOUND));
        }

        return List.of();
    }

    /**
     * 상태 정보 유효성 체크
     * @param memberId
     * @return
     */
    public List<ValidateErrorResponse> checkState(Long memberId) {
        if (!memberInfoRepository.existsById(memberId)) {
            return List.of(new ValidateErrorResponse("memberId", BaseResponseCode.MEMBER_IS_NOT_FOUND));
        }
        return List.of();
    }
}
