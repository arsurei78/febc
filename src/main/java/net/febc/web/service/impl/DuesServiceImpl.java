package net.febc.web.service.impl;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.web.BaseResponse;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.cmmn.web.ValidateErrorResponse;
import net.febc.web.dto.req.dues.ReqChgDto;
import net.febc.web.dto.req.dues.ReqDuesDto;
import net.febc.web.dto.req.dues.ReqInsertDto;
import net.febc.web.dto.req.dues.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.dues.*;
import net.febc.web.repository.first.entity.account.DuesInfo;
import net.febc.web.repository.first.entity.member.MemberInfo;
import net.febc.web.repository.first.impl.DuesRepositoryImpl;
import net.febc.web.repository.first.impl.MemberInfoRepositoryImpl;
import net.febc.web.repository.first.write.DuesInfoRepository;
import net.febc.web.repository.first.write.MemberInfoRepository;
import net.febc.web.service.impl.validate.DuesValidate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DuesServiceImpl {

    private final DuesRepositoryImpl repositoryImpl;
    private final MemberInfoRepository memberInfoRepository;
    private final MemberInfoRepositoryImpl memberInfoRepositoryImpl;
    private final DuesInfoRepository duesInfoRepository;
    private final DuesValidate duesValidate;

    public BaseResponse<ResInsertDto> getUserInfo(Long memberId) {

        if (!memberInfoRepository.existsById(memberId)) {
            return new BaseResponse<>(List.of(new ValidateErrorResponse("error", BaseResponseCode.MEMBER_IS_NOT_FOUND)));
        }
        // 멤버 정보
        ResInsertDto resInsertDto = memberInfoRepositoryImpl.getMemberInfo(memberId);
        // 입금월을 가져옴(마지막 입금월 + 1)
        String latestMonth = repositoryImpl.lastPaymentDate(memberId, 1);
        resInsertDto.setDate(latestMonth);
        return new BaseResponse<>(resInsertDto);
    }

    /**
     * 지정 회원/달의 회비 납입 정보를 가져옴
     * @param duesDto
     * @return
     */
    public ResDuesMonthDto getMemberByMonth(ReqDuesDto duesDto) {
        return repositoryImpl.getMemberByMonth(duesDto);
    }

    /**
     * 조회
     * @param dto
     * @return
     */
    public BaseResponse<BasePaginationDto> getList(ReqListDto dto) {

        if (dto == null) {
            dto = new ReqListDto();
        }
        Page<ResListDto> accountInfoList = repositoryImpl.getDuesInfoList(dto);
        // 멤버 리스트 반환
        return new BaseResponse<>(new BasePaginationDto<>(accountInfoList, dto.getOffset(), Constants.PAGE_BLOCK_SIZE));
    }

    /**
     * 신규 작성
     * @param dto
     * @return
     */
    @Transactional
    public BaseResponse<Long> insertDues(ReqInsertDto dto) {

        List<ValidateErrorResponse> result = duesValidate.checkInsert(dto);
        if(!result.isEmpty()) {
            return new BaseResponse<>(result);
        }

        MemberInfo memberInfo = memberInfoRepository.findById(dto.getMemberId()).orElse(null);
        // 엔티티 작성
        DuesInfo entity = dto.toEntity(memberInfo);
        //
        DuesInfo duesInfo = duesInfoRepository.save(entity);

        return new BaseResponse<>(duesInfo.getId());
    }

    /**
     * 상세
     * @param duesId
     * @return
     */
    public BaseResponse<ResDetailDto> duesDetail(Long duesId) {
        List<ValidateErrorResponse> checkResult = duesValidate.checkDetail(duesId);
        if(!checkResult.isEmpty()) {
            return new BaseResponse<>(checkResult);
        }
        // 회비 납입정보조회
        ResDetailDto duesInfo = repositoryImpl.getDuesInfo(duesId);
        return new BaseResponse<>(duesInfo);
    }

    /**
     * 변경
     * @param chgDto
     * @return
     */
    @Transactional
    public BaseResponse<Long> chgDues(ReqChgDto chgDto) {

        List<ValidateErrorResponse> checkResult = duesValidate.checkChg(chgDto);
        if(!checkResult.isEmpty()) {
            return new BaseResponse<>(checkResult);
        }
        DuesInfo duesInfo = duesInfoRepository.findById(chgDto.getDuesId()).orElse(null);
        duesInfo.setDeposit(chgDto.getPayment());
        duesInfo.setMemo(chgDto.getMemo());
        duesInfo.setDepositAT(LocalDate.now());

        return new BaseResponse<>(duesInfo.getId());
    }
}
