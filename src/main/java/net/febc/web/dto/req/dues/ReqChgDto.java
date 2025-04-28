package net.febc.web.dto.req.dues;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqChgDto {
    // 멤버 번호
    private Long duesId;
    // 납입금액
    private Integer payment;
    // 메모
    private String memo;
}