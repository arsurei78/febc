package net.febc.web.dto.res.account;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.web.repository.comm.ExpensensEnum;

import java.time.LocalDate;

@Getter
@Setter
public class ResListDto {

    // 고유 ID
    private Long id;
    // 타입 지출(O) / 수입 타입(I)"
    private String type;
    // 지출 / 수입 타입(교사 사례비(TS), 지방세(LOT), 소득세(INT), 선물(GIFT), 간식비(SNACK), 악보제본비(SHEET), 기타(ORTHER))"
    private String expensensType;
    // 날짜
    private String date;
    // 금액
    private String amount;

    @QueryProjection
    public ResListDto(Long id,
                      String type,
                      ExpensensEnum expensensEnum,
                      LocalDate date,
                      Integer amount) {
        this.id = id;
        this.type = type;
        this.expensensType = Constants.expensensMap.get(expensensEnum.toString());
        this.date = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD_HAN, date);
        this.amount = CommonUtils.makeMoney(amount);
    }
}
