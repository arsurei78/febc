package net.febc.web.service;


import net.febc.web.dto.res.TokenDto;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Token관련 서비스(refresh Token, Access Token을 작성)
 */
public interface TokenService {

    List<TokenDto> makeToken(HttpServletRequest request) throws EgovBizException;
}
