package egovframework.security;

import egovframework.web.dto.vo.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 로그인 처리
 */
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 입력받은 회원ID
        String loginUserId = String.valueOf(authentication.getPrincipal());
        // 입력받은 패스워드
        String loginPassword = String.valueOf(authentication.getCredentials());
        // 로그인 요청 회원정보 조회
        UserAdapter user = (UserAdapter) userDetailsService.loadUserByUsername(loginUserId);
        // 패스워드 확인
        if (!passwordEncoder.matches(loginPassword, user.getPassword())) {
            throw new BadCredentialsException(loginUserId);
        }
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user, loginPassword);
        return authenticationManager.authenticate(token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}