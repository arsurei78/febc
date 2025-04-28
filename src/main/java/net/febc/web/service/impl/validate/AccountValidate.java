package net.febc.web.service.impl.validate;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.cmmn.web.ValidateErrorResponse;
import net.febc.web.dto.req.account.ReqChgDto;
import net.febc.web.dto.req.account.ReqInsertDto;
import net.febc.web.repository.comm.ExpensensEnum;
import net.febc.web.repository.first.write.ExpensesInfoRepository;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountValidate {

    private final ExpensesInfoRepository repository;

    /**
     * 입력 체크
     * @param dto
     * @return
     */
    public List<ValidateErrorResponse> insertCheck(ReqInsertDto dto) {

        if(!EnumUtils.isValidEnum(ExpensensEnum.class, dto.getExpensensType())) {
            return List.of(new ValidateErrorResponse("expensensType", BaseResponseCode.EXPENSENS_TYPE_NOT_SUPPORT));
        }

        if(StringUtils.isEmpty(dto.getType()) ||
                !List.of(Constants.EXPENSE_TYPE_I, Constants.EXPENSE_TYPE_O).contains(dto.getType())) {
            return List.of(new ValidateErrorResponse("type", BaseResponseCode.TYPE_NOT_SUPPORT));
        }

        if(dto.getAmount() == null || dto.getAmount().equals(Integer.valueOf(0))) {
            return List.of(new ValidateErrorResponse("amount", BaseResponseCode.AMOUNT_NOT_INPUT));
        }

        if(StringUtils.isEmpty(dto.getSelectedDate())) {
            return List.of(new ValidateErrorResponse("selectedDate", BaseResponseCode.EXPENSENS_SELECTED_DATE));
        }
        try{
            CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, dto.getSelectedDate());
        } catch (Exception e) {
            return List.of(new ValidateErrorResponse("selectedDate", BaseResponseCode.EXPENSENS_SELECTED_DATE));
        }

        return List.of();
    }

    /**
     * 업데이트 체크
     * @param dto
     * @return
     */
    public List<ValidateErrorResponse> chgAccount(ReqChgDto dto) {

        if(!repository.existsById(dto.getId())) {
            return List.of(new ValidateErrorResponse("id", BaseResponseCode.ACCOUNT_NOT_FOUND));
        }

        if(!EnumUtils.isValidEnum(ExpensensEnum.class, dto.getExpensensType())) {
            return List.of(new ValidateErrorResponse("expensensType", BaseResponseCode.EXPENSENS_TYPE_NOT_SUPPORT));
        }

        if(StringUtils.isEmpty(dto.getType()) ||
                !List.of(Constants.EXPENSE_TYPE_I, Constants.EXPENSE_TYPE_O).contains(dto.getType())) {
            return List.of(new ValidateErrorResponse("type", BaseResponseCode.TYPE_NOT_SUPPORT));
        }

        if(dto.getAmount() == null || dto.getAmount().equals(Integer.valueOf(0))) {
            return List.of(new ValidateErrorResponse("amount", BaseResponseCode.AMOUNT_NOT_INPUT));
        }

        if(StringUtils.isEmpty(dto.getMemo())) {
            return List.of(new ValidateErrorResponse("amount", BaseResponseCode.MEMO_NOT_INPUT));
        }

        if(StringUtils.isEmpty(dto.getSelectedDate())) {
            return List.of(new ValidateErrorResponse("selectedDate", BaseResponseCode.EXPENSENS_SELECTED_DATE));
        }
        try{
            CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, dto.getSelectedDate());
        } catch (Exception e) {
            return List.of(new ValidateErrorResponse("selectedDate", BaseResponseCode.EXPENSENS_SELECTED_DATE));
        }

        return List.of();
    }
}
