package egovframework.web.service.impl;


import egovframework.web.dto.vo.UserAdapter;
import egovframework.web.repository.first.entity.user.UserInfo;
import egovframework.web.repository.first.read.UserInfoReadRepository;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        UserInfo user = userInfoReadRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
        return new UserAdapter(user);
    }
}
