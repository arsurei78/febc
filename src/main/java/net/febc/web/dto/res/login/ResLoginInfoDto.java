package net.febc.web.dto.res.login;

import lombok.Getter;
import lombok.Setter;
import net.febc.web.dto.res.TokenDto;

@Getter
@Setter
public class ResLoginInfoDto {

    //@ApiModelProperty(value = "사원 번호")
    private String userId;
    //@ApiModelProperty(value = "사원 이름")
    private String username;
    //@ApiModelProperty(value = "Access 토큰 정보")
    private TokenDto accessDto;
    //@ApiModelProperty(value = "Refresh 토큰 정보")
    private TokenDto refreshDto;

}
