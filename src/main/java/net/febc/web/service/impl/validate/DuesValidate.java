package net.febc.web.service.impl.validate;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.cmmn.web.ValidateErrorResponse;
import net.febc.web.dto.req.dues.ReqChgDto;
import net.febc.web.dto.req.dues.ReqInsertDto;
import net.febc.web.repository.first.write.DuesInfoRepository;
import net.febc.web.repository.first.write.MemberInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DuesValidate {
    private final MemberInfoRepository repository;
    private final DuesInfoRepository duesRepository;

    public List<ValidateErrorResponse> checkInsert(ReqInsertDto dto) {
        // 회원정보 체크
        if(!repository.existsByIdAndState(dto.getMemberId(), Boolean.TRUE)) {
            return List.of(new ValidateErrorResponse("", BaseResponseCode.MEMBER_IS_NOT_FOUND));
        }

        try {
            CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, dto.getDate() + "-01");
        }catch (DateTimeParseException e) {
            return List.of(new ValidateErrorResponse("date", BaseResponseCode.DUES_SELECTED_DATE));
        }
        // 납입금 체크
        if(dto.getPayment() == null || dto.getPayment() < 0) {
            return List.of(new ValidateErrorResponse("deposit", BaseResponseCode.DUES_SELECTED_DATE));
        }

        return List.of();
    }

    /**
     * 상세 화면 유효성 체크
     * @param duesId
     * @return
     */
    public List<ValidateErrorResponse> checkDetail(Long duesId) {
        if(!duesRepository.existsById(duesId)) {
            return List.of(new ValidateErrorResponse("error", BaseResponseCode.DUES_NOT_FOUND));
        }
        return List.of();
    }

    /**
     * 변경 유효성 체크
     * @param chgDto
     * @return
     */
    public List<ValidateErrorResponse> checkChg(ReqChgDto chgDto) {
        if(!duesRepository.existsById(chgDto.getDuesId())) {
            return List.of(new ValidateErrorResponse("error", BaseResponseCode.DUES_NOT_FOUND));
        }

        if(chgDto.getPayment() == null || chgDto.getPayment() < 0) {
            return List.of(new ValidateErrorResponse("deposit", BaseResponseCode.DUES_SELECTED_DATE));
        }

        return List.of();
    }
}
