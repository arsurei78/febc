package net.febc.web.dto.res;

import lombok.Data;

@Data
public class TokenDto {
    /**
     * 토큰 타입 (access_token / refresh_token)
     */
    private String type;
    /**
     * 토큰 정보
     */
    private String token;
    /**
     * 만료시간
     */
    private String expireTime;


    public TokenDto(String type, String token, String expireTime) {
        this.type = type;
        this.token = token;
        this.expireTime = expireTime;
    }
}
