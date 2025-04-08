package net.febc.web.dto.req.account;

import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.web.repository.comm.ExpensensEnum;
import net.febc.web.repository.first.entity.account.ExpensesInfo;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReqInsertDto {

    // 지출(O) / 수입 타입(I)
    private String type;
    // 지출 / 수입 타입(교사 사례비(TS), 지방세(LOT), 소득세(INT), 선물(GIFT), 간식비(SNACK), 악보제본비(SHEET), 기타(ORTHER))
    private String expensensType;
    // 날짜
    private String selectedDate;
    // 금액
    private Integer amount;
    // 메모
    private String memo;


    /**
     * 입력받은 데이터를 entity로변환
     * @return
     */
    public ExpensesInfo toEntity() {
        return ExpensesInfo
                .builder()
                .type(type)
                .date(CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, selectedDate))
                .expensType(ExpensensEnum.valueOf(expensensType))
                .amount(amount)
                .memo(memo)
                .build();

    }
}
