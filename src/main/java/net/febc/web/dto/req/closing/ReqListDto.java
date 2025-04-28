package net.febc.web.dto.req.closing;

import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.YearMonth;

@Getter
@Setter
public class ReqListDto {

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
}
