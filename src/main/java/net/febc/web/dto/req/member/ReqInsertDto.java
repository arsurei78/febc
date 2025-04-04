package net.febc.web.dto.req.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.febc.web.repository.first.entity.member.MemberInfo;

import java.time.LocalDate;

@Getter
@Setter
@ApiModel(value = "memberRequest", description = "멤버 정보 작성")
public class ReqInsertDto {
    
    @ApiModelProperty(value = "이름", example = "홍길동")
    private String name;
    @ApiModelProperty(value = "성별(M:남성, F:여성)", example = "F")
    private String gender;
    @ApiModelProperty(value = "기수", example = "1")
    private Integer generation;
    @ApiModelProperty(value = "회비", example = "70000")
    private Integer dues;

    /**
     * 생성
     * @return MemberInfo
     */
    public MemberInfo toEntity() {
        return MemberInfo
                .builder()
                .name(name)
                .gender(gender)
                .generation(generation)
                .dues(dues)
                .joinAt(LocalDate.now())
                .state(Boolean.TRUE)
                .build();
    }
}
