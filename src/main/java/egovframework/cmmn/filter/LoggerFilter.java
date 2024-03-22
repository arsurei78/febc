package egovframework.cmmn.filter;

import egovframework.cmmn.utils.CommonUtils;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 로그 파일에 접속 IP/ 세션 정보를 추가
 */
@Component
public class LoggerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String clientIP = CommonUtils.getClientIP((HttpServletRequest) request);
        String sessionId = ((HttpServletRequest) request).getSession().getId();
        MDC.put("ClientIP", clientIP);
        MDC.put("sessionId", sessionId);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
