package net.febc.web.dto.res.account;

import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.web.repository.first.entity.account.ExpensesInfo;

@Getter
@Setter
public class ResDetailDto {

    private Long id;
    private String type;
    private String expensensType;
    private String date;
    private String amount;
    private String memo;

    /**
     *
     * @param info
     */
    public ResDetailDto(ExpensesInfo info) {
        id = info.getId();
        type = info.getType();
        expensensType = Constants.expensensMap.get(info.getExpensType().toString());
        amount = CommonUtils.makeMoney(info.getAmount());
        date = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMMDD, info.getDate());
        memo = info.getMemo();
    }
}
