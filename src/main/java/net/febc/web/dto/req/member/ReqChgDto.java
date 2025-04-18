package net.febc.web.dto.req.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
