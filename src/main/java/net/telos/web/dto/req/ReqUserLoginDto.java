package net.telos.web.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUserLoginDto {
    private String userId;
    private String password;
}
