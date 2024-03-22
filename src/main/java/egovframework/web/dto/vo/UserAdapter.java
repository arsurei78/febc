package egovframework.web.dto.vo;

import egovframework.cmmn.utils.Aes;
import egovframework.web.repository.first.entity.user.UserInfo;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * Security인증용 Vo
 */
public class UserAdapter extends User {

    private UserInfo userInfo;

    @Getter
    private String uname;


    /**
     * Security에서 사용하는 User정보를 Wrapper
     * @param userInfo 사용자 정보
     */
    public UserAdapter(UserInfo userInfo) {
        //상속 받은 User정보 작성
        super(userInfo.getUserId(), userInfo.getPassword(), Collections.singletonList(userInfo.getAuthorities()));
        this.userInfo = userInfo;
        this.uname = Aes.decryption(userInfo.getUsername());
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

}