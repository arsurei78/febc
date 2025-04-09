package net.febc.web.service.impl;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.cmmn.web.BaseResponse;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.cmmn.web.ValidateErrorResponse;
import net.febc.web.dto.req.account.ReqChgDto;
import net.febc.web.dto.req.account.ReqInsertDto;
import net.febc.web.dto.req.account.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.account.ResDetailDto;
import net.febc.web.dto.res.account.ResListDto;
import net.febc.web.repository.comm.ExpensensEnum;
import net.febc.web.repository.first.entity.account.ExpensesInfo;
import net.febc.web.repository.first.impl.AccountInfoRepositoryImpl;
import net.febc.web.repository.first.write.ExpensesInfoRepository;
import net.febc.web.service.impl.validate.AccountValidate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl {

    private final AccountValidate validate;
    private final ExpensesInfoRepository repository;
    private final AccountInfoRepositoryImpl repositoryImpl;

    /**
     * 입력
     * @param dto
     * @return
     */
    @Transactional
    public BaseResponse<ResDetailDto> insert(ReqInsertDto dto) {
        List<ValidateErrorResponse> checkResult = validate.insertCheck(dto);
        if(!checkResult.isEmpty()) {
            return new BaseResponse<>(checkResult);
        }
        ExpensesInfo entity = dto.toEntity();
        repository.save(entity);
        return new BaseResponse<>(new ResDetailDto(entity));
    }

    /**
     * 수정
     * @param dto
     * @return
     */
    @Transactional
    public BaseResponse<ResDetailDto> chgAccount(ReqChgDto dto) {

        List<ValidateErrorResponse> result = validate.chgAccount(dto);
        if(!result.isEmpty()) {
            return new BaseResponse<>(result);
        }
        ExpensesInfo expensesInfo = repository.findById(dto.getId()).orElse(null);
        expensesInfo.setAmount(dto.getAmount());
        expensesInfo.setDate(CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, dto.getSelectedDate()));
        expensesInfo.setType(dto.getType());
        expensesInfo.setExpensType(ExpensensEnum.valueOf(dto.getExpensensType()));
        expensesInfo.setMemo(dto.getMemo());
        return new BaseResponse<>(new ResDetailDto(expensesInfo));
    }

    /**
     * 지출 정보 상세
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public BaseResponse<ResDetailDto> detailAccount(Long id) {
        // 멤버 정보 조회
        ExpensesInfo expensesInfo = repository.findById(id).orElse(null);
        // 멤버 정보가 없는 경우,
        if(expensesInfo == null) {
            return new BaseResponse<>(List.of(new ValidateErrorResponse("id", BaseResponseCode.ACCOUNT_NOT_FOUND)));
        }
        return new BaseResponse<>(new ResDetailDto(expensesInfo));
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
        Page<ResListDto> accountInfoList = repositoryImpl.getAccountInfoList(dto);
        // 멤버 리스트 반환
        return new BaseResponse<>(new BasePaginationDto<>(accountInfoList, dto.getOffset(), Constants.PAGE_BLOCK_SIZE));
    }
}
