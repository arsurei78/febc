package net.telos.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import net.telos.cmmn.constant.AppConstants;
import net.telos.cmmn.constant.Constants;
import net.telos.cmmn.utils.CommonUtils;
import net.telos.cmmn.web.BaseResponseCode;
import net.telos.web.dto.res.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;

import static net.telos.cmmn.constant.Constants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * JWT관련 UTIL클래스
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Autowired
    private final AppConstants appConstants;

    /**
     * JWT토큰의 유효성 체크
     * @param request
     * @return
     */
    public Object checkJwt(HttpServletRequest request) {
        // 헤더의 인증정보를 습득
        String authorization = request.getHeader(AUTHORIZATION);

        // 인증정보가 없는 경우 에러반환
        if (!StringUtils.hasText(authorization) 
                || !authorization.startsWith(TOKEN_HEADER_PREFIX)){
            // 헤더의 인증 토큰 에러
            return BaseResponseCode.JWT_AUTHORIZATION_HEADER;
        }

        String accessToken = authorization.substring(TOKEN_HEADER_PREFIX.length());

        DecodedJWT verify;

        try {
            // 검증클래스 생성
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(appConstants.getMJWT_SECRET())).build();
            // 토큰 검증
            verify = verifier.verify(accessToken);
        } catch (TokenExpiredException e) {
            // 토큰 기간 만료 에러
            return BaseResponseCode.JWT_TOKEN_EXPIRED;
        } catch (AlgorithmMismatchException
                 | SignatureVerificationException
                 | InvalidClaimException e ) {
            return BaseResponseCode.JWT_AUTHORIZATION_ERROR;
        } catch (RuntimeException e) {
            // 기타 에러
            return BaseResponseCode.JWT_TOKEN_ERROR;
        }

        return verify;
    }

    /**
     * Access-Token 작성
     *
     * @param userId 회원ID
     * @param authorities 권한
     * @param loginToken 로그인토큰 정보
     * @return String token값
     */
    public TokenDto makeAccessToken(String userId, Collection<? extends GrantedAuthority> authorities, String loginToken) {
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        // 외부 요청
        long expire = appConstants.getMJWT_AT_EXP_TIME();
        String secretKey = appConstants.getMJWT_SECRET();

        Date expireTime = Timestamp.valueOf(now.plusSeconds(expire));

        String token = JWT.create()
                .withSubject(userId)
                .withExpiresAt(expireTime)
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .withClaim(LOGIN_TOKEN, loginToken)
                .withIssuedAt(Timestamp.valueOf(now))
                .sign(Algorithm.HMAC256(secretKey));
        // 현재 시간
        LocalDateTime localDateTime = new Timestamp(expireTime.getTime()).toLocalDateTime();
        return new TokenDto(Constants.AT_HEADER, token, CommonUtils.dateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS, localDateTime));
    }

    /**
     * Refresh-Token 작성
     *
     * @param userId 회원ID
     * @return String token값
     */
    public TokenDto makeRefreshToken(String userId) {
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        // 외부 요청
        long expire = appConstants.getMJWT_RT_EXP_TIME();
        String secretKey = appConstants.getMJWT_SECRET();
        Date expireTime = Timestamp.valueOf(now.plusSeconds(expire));
        String token = JWT.create()
                .withSubject(userId)
                .withExpiresAt(expireTime)
                .withIssuedAt(Timestamp.valueOf(now))
                .sign(Algorithm.HMAC256(secretKey));
        // 현재 시간
        LocalDateTime localDateTime = new Timestamp(expireTime.getTime()).toLocalDateTime();
        return new TokenDto(Constants.RT_HEADER, token, CommonUtils.dateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS, localDateTime));
    }

}
