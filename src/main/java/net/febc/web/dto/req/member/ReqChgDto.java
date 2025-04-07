package net.febc.web.dto.req.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "memberRequest", description = "멤버 정보 수정")
public class ReqChgDto {
    @ApiModelProperty(value = "멤버 고유 번호", example = "1")
    private Long id;

    @ApiModelProperty(value = "기수", example = "1")
    private Integer generation;

    @ApiModelProperty(value = "회비", example = "70000")
    private Integer dues;

    @ApiModelProperty(value = "성별", example = "M:남자, F:여자")
    private String gender;
}
