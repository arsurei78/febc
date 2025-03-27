package net.febc.web.dto.req.user;


import net.febc.web.repository.first.entity.user.UserInfo;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * Security인증용 Vo
 */
public class UserAdapter extends User {

    private UserInfo userInfo;

    /**
     * Security에서 사용하는 User정보를 Wrapper
     * @param account
     */
    public UserAdapter(UserInfo account) {
        //상속 받은 User정보 작성
        super(account.getUserId(), account.getPassword(), Collections.singletonList(account.getAuthorities()));
        this.userInfo = account;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}