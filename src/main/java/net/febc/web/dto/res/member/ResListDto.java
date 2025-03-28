package net.febc.web.dto.res.member;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResListDto {

    @ApiModelProperty(value = "멤버 고유 ID" , example = "1")
    private Long memberId;
    @ApiModelProperty(value = "이름" , example = "홍길동")
    private String name;
    @ApiModelProperty(value = "기수" , example = "1")
    private Integer generation;
    @ApiModelProperty(value = "상태" , example = "true")
    private Boolean state;

    @QueryProjection
    public ResListDto(Long id,
                      String name,
                      Integer generation,
                      Boolean state) {
        this.memberId = id;
        this.name = name;
        this.generation = generation;
        this.state = state;
    }
}
