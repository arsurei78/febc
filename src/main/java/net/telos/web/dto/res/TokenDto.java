package net.telos.web.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenDto {
    /**
     * 토큰 타입 (access_token / refresh_token)
     */
    @Schema(description = "토큰 타입(access_token / refresh_token)")
    private String type;
    /**
     * 토큰 정보
     */
    @Schema(description = "토큰 값")
    private String token;
    /**
     * 만료시간
     */
    @Schema(description = "토큰 만료 시간")
    private String expireTime;


    public TokenDto(String type, String token, String expireTime) {
        this.type = type;
        this.token = token;
        this.expireTime = expireTime;
    }
}
