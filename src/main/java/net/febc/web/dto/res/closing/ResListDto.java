package net.febc.web.dto.res.closing;

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
    // 타이틀
    private String title;
    // 날짜
    private String date;
    // 삭제 여부(true:삭제)
    private boolean deleteFlg;

    @QueryProjection
    public ResListDto(Long id,
                      String title,
                      LocalDate startDate,
                      LocalDate endDate,
                      boolean deleteFlg) {
        this.id = id;
        this.title = title;
        this.date = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD, startDate) +
                "-" + CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD, endDate);
        this.deleteFlg = deleteFlg;
    }
}
