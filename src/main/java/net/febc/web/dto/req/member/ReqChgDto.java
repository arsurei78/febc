package net.febc.web.dto.req.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "memberRequest", description = "멤버 정보 수정")
public class ReqChgDto {
    // 멤버 고유 번호
    private Long id;
    // 기수
    private Integer generation;
    // 회비
    private Integer dues;
    // 성별
    private String gender;
}
