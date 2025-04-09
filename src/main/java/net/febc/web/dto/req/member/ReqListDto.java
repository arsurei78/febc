package net.febc.web.dto.req.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;

@Getter
@Setter
@ApiModel(value = "memberRequest", description = "멤버 정보 리스트")
public class ReqListDto {

    // 검색어(이름)
    private String searchData;
    // 상태(입/퇴단 여부, Y:입단, N:퇴단)
    private String state;
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
