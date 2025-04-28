package net.febc.web.dto.res.closing.detail;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.web.repository.comm.ExpensensEnum;

@Getter
@Setter
public class DetailInfo {
    // 내용
    private String title;
    // 금액
    private Integer amount;
    // 금액(원화)
    private String strAmount;
    // 비고
    private String memo;
    // 타입
    private String type;

    @QueryProjection
    public DetailInfo(ExpensensEnum title,
                      Integer amount,
                      String memo,
                      String type) {
        this.title = Constants.expensensMap.get(title.toString());
        this.amount = amount;
        this.strAmount = CommonUtils.makeMoney(amount);
        this.memo = memo;
        this.type = type;
    }

    public DetailInfo(Integer totalDues) {
        this.title = "회비";
        this.amount = totalDues;
        this.strAmount = CommonUtils.makeMoney(totalDues);
        this.memo = "";
        this.type = Constants.EXPENSE_TYPE_I;
    }
}
