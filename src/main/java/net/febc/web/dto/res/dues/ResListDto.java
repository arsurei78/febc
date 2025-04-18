package net.febc.web.dto.res.dues;

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
    // 납입 대상월
    private String paymentMonth;
    // 납입일
    private String date;
    // 납입금액
    private Integer amount;
    // 납입대상
    private String name;
    // 기수
    private Integer generation;

    @QueryProjection
    public ResListDto(Long id,
                      LocalDate date,
                      Integer year,
                      Integer month,
                      Integer generation,
                      Integer amount,
                      String name) {
        this.id = id;
        this.date = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD_HAN, date);
        this.paymentMonth = String.format("%d년 %d월", year, month);
        this.amount = amount;
        this.generation = generation;
        this.name = name;
    }
}
