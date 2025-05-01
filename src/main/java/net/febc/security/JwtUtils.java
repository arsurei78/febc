package net.febc.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.AppConstants;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.cmmn.web.BaseResponseCode;
import net.febc.web.dto.res.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import static net.febc.cmmn.constant.Constants.*;
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
            e.fillInStackTrace();
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
        // 외부 요청
        long expire = appConstants.getMJWT_AT_EXP_TIME();
        String secretKey = appConstants.getMJWT_SECRET();

        Instant now = Instant.now(); // 항상 UTC 기준

        String token = JWT.create()
                .withSubject(userId)
                .withExpiresAt(Date.from(now.plusSeconds(expire)))           //  UTC 기준 만료시간
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .withClaim(LOGIN_TOKEN, loginToken)
                .withIssuedAt(Date.from(now))                                //  UTC 기준 발급시간
                .sign(Algorithm.HMAC256(secretKey));

        LocalDateTime localExpireTime = LocalDateTime.ofInstant(now.plusSeconds(expire), ZoneId.of("Asia/Seoul"));
        return new TokenDto(Constants.AT_HEADER, token, CommonUtils.dateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS, localExpireTime));

    }


    public ResponseCookie makeCookie(Authentication authResult) {
        // 쿠키를 작성
        TokenDto tokenDto = makeAccessToken(authResult.getName(), authResult.getAuthorities(), UUID.randomUUID().toString());
        // 만료시간
        long expire = appConstants.getMJWT_AT_EXP_TIME();
        return ResponseCookie.from("jwt", tokenDto.getToken())
                .httpOnly(true)
                .sameSite("Lax")
                //.secure(true) // https의 경우, 활성화
                .path("/")
                .maxAge(Duration.ofSeconds(expire))
                .build();
    }

    private String extractTokenFromCookies(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * JWT토큰의 유효성 체크
     * @param request
     * @return
     */
    public Object checkCookieJwt(HttpServletRequest request) {
        // 쿠키에서 정보를 조회
        String authorization = extractTokenFromCookies(request.getCookies());

        // 인증정보가 없는 경우 에러반환
        if (!StringUtils.hasText(authorization)){
            // 인증 토큰 에러
            return BaseResponseCode.JWT_AUTHORIZATION_HEADER;
        }

        DecodedJWT verify;

        try {
            // 검증클래스 생성
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(appConstants.getMJWT_SECRET())).build();
            // 토큰 검증
            verify = verifier.verify(authorization);
        } catch (Exception e) {
            System.out.println("+++++++++++++++++++++++++++");
            System.out.println("MESSAGE" + e.getMessage());
            System.out.println("ERROR" + e);
            System.out.println("+++++++++++++++++++++++++++");
            // 기타 에러
            return BaseResponseCode.JWT_TOKEN_ERROR;
        }

        return verify;
    }

    public boolean isTokenExpiringSoon(HttpServletRequest request) {

        String authorization = extractTokenFromCookies(request.getCookies());
        String secretKey = appConstants.getMJWT_SECRET();

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(authorization)
                .getBody();

        Date expiration = claims.getExpiration();
        long timeToExpire = expiration.getTime() - System.currentTimeMillis();

        return timeToExpire <= 60_000;  // 60,000ms = 1분
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
