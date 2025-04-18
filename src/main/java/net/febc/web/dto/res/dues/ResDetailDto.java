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
    private Long id;
    // 기수
    private Integer generation;
    // 이름
    private String name;
    // 납부 예정액
    private Integer exPayAmount;
    // 납부 액
    private Integer payAmount;
    // 납입 대상월
    private String paymentMonth;
    // 납부일
    private String date;

    @QueryProjection
    public ResDetailDto(Long id,
                        Integer generation,
                        String name,
                        Integer exPayAmount,
                        Integer payAmount,
                        LocalDate paymentMonth,
                        LocalDate date) {
        this.id = id;
        this.generation = generation;
        this.name = name;
        this.exPayAmount = exPayAmount;
        this.payAmount = payAmount;
        this.paymentMonth = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMM, paymentMonth);;
        this.date = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD_HAN, date);
    }
}
