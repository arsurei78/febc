package net.telos.web.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import net.telos.cmmn.web.BaseResponseCode;
import net.telos.security.JwtUtils;
import net.telos.web.dto.res.TokenDto;
import net.telos.web.repository.first.entity.user.UserInfo;
import net.telos.web.repository.first.read.UserInfoReadRepository;
import net.telos.web.service.TokenService;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Token 관련 구현 서비스(refresh Token, Access Token을 작성)
 */
@Service("TokenService")
@RequiredArgsConstructor
public class TokenServiceImpl extends EgovAbstractServiceImpl implements TokenService {

    private final JwtUtils jwtUtils;
    private final ReloadableResourceBundleMessageSource messageSource;

    private final UserInfoReadRepository userInfoRepository;

    /**
     * Refresh Token에 의해 Access Token / Refresh Token을 작성
     * Refresh Token의 만료시간이 지정 시간 이상의 경ㅇ, Access Token만 작성
     * Refresh Token의 만료시간이 지정 시간 이하의 경우, Access Token과 Refresh Token을 작성
     * @param request HttpServletRequest
     * @return List<TokenDto> 토큰 정보
     */
    @Override
    public List<TokenDto> makeToken(HttpServletRequest request) throws EgovBizException {
        // 토큰정보
        List<TokenDto> list = new ArrayList<>();

        Object checkJwt = jwtUtils.checkJwt(request);
        // 반환값이 DecodedJWT형이 아닌경우, 에러
        if (!(checkJwt instanceof DecodedJWT)) {
            String errCode = (String)checkJwt;
            throw new EgovBizException(messageSource, errCode);
        }
        DecodedJWT decodedJWT = (DecodedJWT)checkJwt;
        String username = decodedJWT.getSubject();
        // 회원정보를 습득
        UserInfo userInfo = userInfoRepository.findByUserId(username)
                .orElseThrow(() -> new EgovBizException(messageSource, BaseResponseCode.NOT_FOUND_USER));

        // 2중 로그인 체크
        // DB의 refresh토큰정보와 jwt의 refresh토큰 정보가 다른경우
        if (!userInfo.getRefreshToken().equals(((DecodedJWT) checkJwt).getToken())) {
            throw new EgovBizException(messageSource, BaseResponseCode.JWT_TOKEN_ERROR);
        }
        // 권한정보
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(userInfo.getAuthorities());

        // Access-Token 작성
        TokenDto accessDto = jwtUtils.makeAccessToken(userInfo.getUserId(), roles, userInfo.getApiToken());
        list.add(accessDto);

        Date atExpire = decodedJWT.getExpiresAt();
        // 토큰 만료시간
        LocalDateTime expireDateTime = new Timestamp(atExpire.getTime()).toLocalDateTime();
        // 리프레쉬 토큰 만료시간 체크
        if (LocalDateTime.now().isAfter(expireDateTime.minusMinutes(5))) {
            // Access-Token 작성
            TokenDto refreshToken = jwtUtils.makeRefreshToken(userInfo.getUserId());
            list.add(refreshToken);
        }
        return list;
    }

}
