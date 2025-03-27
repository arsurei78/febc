package net.febc.web.service.impl;


import net.febc.cmmn.constant.Constants;
import net.febc.web.dto.req.user.UserAdapter;
import net.febc.web.repository.first.entity.user.UserInfo;
import net.febc.web.repository.first.read.UserInfoReadRepository;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

/**
 * Spring Security의 UserDetailsService의 구현
 */
@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl extends EgovAbstractServiceImpl implements UserDetailsService {

    private final UserInfoReadRepository userInfoReadRepository;

    /**
     * 로그인 인증
     *
     * @param userId the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 회원ID에 의해 회원정보를 조회, 조회 결과가 없는경우, UsernameNotFoundException를 반환
        //UserInfo user = userInfoReadRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
        // TODO 더미
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("test");
        userInfo.setAuthorities(new SimpleGrantedAuthority(Constants.ROLE_ADMIN));
        userInfo.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password"));
        return new UserAdapter(userInfo);
    }
}
