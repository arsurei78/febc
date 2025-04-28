package net.febc.web.dto.res.member;

import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.web.repository.first.entity.member.MemberInfo;

@Getter
@Setter
public class ResDetailDto {

    //@ApiModelProperty(value = "회원 고유 번호", example = "1")
    private Long id;
    //@ApiModelProperty(value = "이름", example = "홍길동")
    private String name;
    //@ApiModelProperty(value = "성별(M:남성, F:여성)", example = "F")
    private String gender;
    //@ApiModelProperty(value = "기수", example = "1")
    private Integer generation;
    //@ApiModelProperty(value = "회비", example = "70000")
    private String dues;
    //@ApiModelProperty(value = "상태", example = "true")
    private boolean state;

    /**
     * 생성자
     * @param memberInfo
     */
    public ResDetailDto(MemberInfo memberInfo) {
        this.id = memberInfo.getId();
        this.name = memberInfo.getName();
        this.gender = memberInfo.getGender();
        this.generation = memberInfo.getGeneration();
        this.dues = CommonUtils.makeMoney(memberInfo.getDues());
        this.state = memberInfo.getState();
    }
}
