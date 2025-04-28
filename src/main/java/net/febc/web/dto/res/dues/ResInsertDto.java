package net.febc.web.dto.res.dues;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.utils.CommonUtils;

@Getter
@Setter
public class ResInsertDto {
    // 회원 고유 번호
    private Long memberId;
    // 회원정보
    private String name;
    // 회비
    private String dues;
    // 입금액
    private Integer payment;
    // 납입월
    private String date;

    @QueryProjection
    public ResInsertDto(Long memberId,
                        String name,
                        Integer dues) {

        this.memberId = memberId;
        this.name = name;
        this.dues = CommonUtils.makeMoney(dues);
    }
}
