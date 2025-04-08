package net.febc.web.dto.req.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;

import java.time.LocalDate;

@Getter
@Setter
public class ReqListDto {

    // 검색어
    private String searchData;
    // 타입 지출(O) / 수입 타입(I)"
    private String type;
    // 지출 / 수입 타입(교사 사례비(TS), 지방세(LOT), 소득세(INT), 선물(GIFT), 간식비(SNACK), 악보제본비(SHEET), 기타(ORTHER))"
    private String expensensType;
    // 검색 시작일
    private String startDate;
    // 검색 종료일
    private String endDate;
    // 검색 페이지
    private Integer page;
    // 페이지당 출력 건수
    private Integer offset;

    // 페이지 값 정리
    public Integer getPage() {
        if (this.page == null || this.page < 1 ) {
            return Integer.valueOf(0);
        }
        return this.page - 1;
    }

    // 출력 건수
    public Integer getOffset() {
        if (this.offset == null || this.offset < 1) {
            return Constants.DEFAULT_BLOCK_SIZE;
        }
        return this.offset;
    }

    /**
     * 검색 시작일
     * @return
     */
    public LocalDate getStartDate() {
        return CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, this.startDate);
    }

    /**
     * 검색 종료일
     * @return
     */
    public LocalDate getEndDate() {
        return CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, this.endDate);
    }
}
