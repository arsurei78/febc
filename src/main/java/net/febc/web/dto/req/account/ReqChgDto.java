package net.febc.web.dto.req.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqChgDto {

    // 고유ID
    private Long id;
    // 지출(O) / 수입 타입(I)
    private String type;
    // 지출 / 소비 타입(교사 사례비(TS), 지방세(LOT), 소득세(INT), 선물(GIFT), 간식비(SNACK), 악보제본비(SHEET), 기타(ORTHER))
    private String expensensType;
    // 날짜
    private String selectedDate;
    // 금액
    private Integer amount;
    // 메모
    private String memo;
}
