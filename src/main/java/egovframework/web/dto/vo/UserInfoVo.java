package egovframework.web.dto.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoVo {
    private String userId;
    private String nickname;
    private String token;

    public UserInfoVo(String userId, String nickname, String token) {
        this.userId = userId;
        this.nickname = nickname;
        this.token = token;
    }

}
