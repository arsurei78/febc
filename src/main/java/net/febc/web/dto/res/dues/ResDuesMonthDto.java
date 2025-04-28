package net.febc.web.dto.res.dues;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;

import java.time.LocalDate;

@Getter
@Setter
public class ResDuesMonthDto {
    // 회비 고유번호
    private Long duesId;
    // 납부 예정액
    private Integer exPayment;
    // 납부 회비
    private Integer payment;
    // 메모
    private String memo;
    // 납부 달
    private String date;

    @QueryProjection
    public ResDuesMonthDto(Long duesId,
                           Integer exPayment,
                           Integer payment,
                           String memo,
                           LocalDate duesDate) {
        this.duesId = duesId;
        this.exPayment = exPayment;
        this.memo = memo;
        this.payment = payment;
        this.date = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMM, duesDate);
    }
}
