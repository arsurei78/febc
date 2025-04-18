package net.febc.web.dto.req.member;

import lombok.Getter;
import lombok.Setter;
import net.febc.web.repository.first.entity.member.MemberInfo;

import java.time.LocalDate;

@Getter
@Setter
public class ReqInsertDto {
    
    // 이름
    private String name;
    // 성별(M:남성, F:여성)
    private String gender;
    // 기수
    private Integer generation;
    // 회비
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
