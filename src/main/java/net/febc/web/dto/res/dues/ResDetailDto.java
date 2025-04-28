package net.febc.web.dto.res.dues;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;

import java.time.LocalDate;

@Getter
@Setter
public class ResDetailDto {
    // 회비 납부 고유ID
    private Long duesId;
    // 회원 고유 ID
    private Long memberId;
    // 이름
    private String name;
    // 납부 예정액
    private Integer exPayment;
    // 납부 액
    private String payment;
    // 납입 대상월
    private String paymentMonth;
    // 납부일
    private String date;
    // 메모
    private String memo;

    @QueryProjection
    public ResDetailDto(Long duesId,
                        Long memberId,
                        String name,
                        Integer exPayment,
                        Integer payment,
                        LocalDate paymentMonth,
                        LocalDate duesDate,
                        String memo) {
        this.duesId = duesId;
        this.memberId = memberId;
        this.name = name;
        this.exPayment = exPayment;
        this.payment = CommonUtils.makeMoney(payment);
        this.paymentMonth = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMM_HAN, paymentMonth);;
        this.date = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMM, duesDate);
        this.memo = memo;
    }
}
