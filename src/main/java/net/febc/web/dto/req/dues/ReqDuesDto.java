package net.febc.web.dto.req.dues;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqDuesDto {
    // 회원고유번호
    private String memberId;
    // 납부월
    private String month;

    public String getMonth() {
        return this.month + "-01";
    }
}
