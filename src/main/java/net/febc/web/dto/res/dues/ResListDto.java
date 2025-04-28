package net.febc.web.dto.res.dues;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;

import java.time.LocalDate;

@Getter
@Setter
public class ResListDto {

    // 고유 ID
    private Long id;
    // 납입 대상월
    private String paymentMonth;
    // 납입일
    private String date;
    // 납입금액
    private String payment;
    // 납입대상
    private String name;
    // 기수
    private Integer generation;
    // 미납여부
    private boolean notPayment;

    @QueryProjection
    public ResListDto(Long id,
                      LocalDate duesDate,
                      LocalDate paymentDate,
                      Integer generation,
                      Integer payment,
                      String name) {
        this.id = id;
        this.date = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD_HAN, paymentDate);
        this.paymentMonth = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMM, duesDate);
        this.payment = CommonUtils.makeMoney(payment);
        this.generation = generation;
        this.name = name;
    }
}
