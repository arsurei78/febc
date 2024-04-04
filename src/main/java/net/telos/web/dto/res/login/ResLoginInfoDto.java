package net.telos.web.dto.res.login;

import net.telos.web.dto.res.TokenDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResLoginInfoDto {

    @ApiModelProperty(value = "사원 번호")
    private String userId;
    @ApiModelProperty(value = "사원 이름")
    private String username;
    @ApiModelProperty(value = "Access 토큰 정보")
    private TokenDto accessDto;
    @ApiModelProperty(value = "Refresh 토큰 정보")
    private TokenDto refreshDto;

}
