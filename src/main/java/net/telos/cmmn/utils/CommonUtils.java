package net.telos.cmmn.utils;

import net.telos.cmmn.web.ValidateErrorResponse;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 공통 UTILS
 */
public class CommonUtils {

    private CommonUtils(){}

    /**
     * Date를 지정포멧으로 변경
     * @param format 포멧
     * @param date Date정보
     * @return
     */
    public static String dateFormat (String format, LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Date를 지정포멧으로 변경
     * @param format 포멧
     * @param date Date정보
     * @return
     */
    public static Integer dateFormatInt (String format, LocalDateTime date) {
        return Integer.valueOf(date.format(DateTimeFormatter.ofPattern(format)));
    }

    /**
     * LocaleDateTime 포멘 변경
     * @param format 포멧
     * @param date LocaleDateTime정보
     * @return
     */
    public static String localDateTimeFormat(String format, LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * localDate 포멘 변경
     * @param format 포멧
     * @param date LocaleDateTime정보
     * @return
     */
    public static String localDateFormat(String format, LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Integer 타입의 date를 받아서 dateFormat1타입의 Date타입으로 변경후, dateFormat2타입의 문자열로 변경
     *
     * @param dateFormat1
     * @param dateFormat2
     * @param date
     * @return
     * @throws ParseException
     */
    public static String intToDateFormat(String dateFormat1, String dateFormat2, Integer date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat1);
        Date scheduleDate = format.parse(String.valueOf(date));
        LocalDateTime localDate = new java.sql.Timestamp(scheduleDate.getTime()).toLocalDateTime();
        return CommonUtils.dateFormat(dateFormat2, localDate);
    }

    /**
     * 지정 폴더의 파일 삭제(폴더의 경우, 하위 폴더/ 파일까지 전부 삭제)
     * 
     * @param rootFile 삭제 파일 / 폴더
     * @return true: 성공
     */
    public static void deleteFiles(File rootFile) {
        File[] allFiles = rootFile.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.isFile() ) {
                    file.delete();
                } else {
                    deleteFiles(file);
                }
            }
        }
    }

    /**
     *  파일 사이즈를 계산
     * @param filesize
     * @return
     */
    public static double getFileSize(long filesize) {
        BigDecimal fileSize = new BigDecimal(filesize);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        return fileSize.divide(megabyte, 2, RoundingMode.FLOOR).doubleValue();
    }

    /**
     * String의 Double 변환 가능 여부 판단
     * @param value 판단 값
     * @return
     */
    public static boolean isDouble(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }

        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 접속 IP를 가져옴
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        // 복수의 IP정보를 가질경우(proxy, Caching server, Load Balancer를  거칠경우 판단)
        if (isMultipleIp(ip)) {
            ip = getMultiClientIp(ip);
        }

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 복수의 IP정보를 가질경우
     * @param ip
     * @return
     */
    private static boolean isMultipleIp(String ip) {
        return StringUtils.contains(ip, ",");
    }

    /**
     * 복수의 IP를 가질경우, 클라이언트 IP(맨 XFF규칙상 맨 처음 IP)
     * @param ipList
     * @return
     */
    private static String getMultiClientIp(String ipList) {
        // X-Forwarded-For의 규칙상 맨 앞의 IP가 클라이언트 IP
        return StringUtils.split(ipList, ",")[0];
    }

    /**
     * 인증 제외 URL확인 (특정 패스이하 전부 허용의 경우 [/패스/**], 그이외의 경우 완전일치만 허용)
     * @param urlPattern
     * @param url
     * @return
     */
    public static boolean urlMatch(String urlPattern, String url){
        // path 디렉토리이하를 전부 허용한 경우
        if (urlPattern.endsWith("/**")){
            String match = urlPattern.substring(0, urlPattern.lastIndexOf("**"));
            String matchEqual = urlPattern.substring(0, urlPattern.lastIndexOf("/**"));
            return url.startsWith(match) || url.equals(matchEqual);
        } else {
            // 단일패스 허용
            if (org.springframework.util.StringUtils.pathEquals(urlPattern, url)){
                return true;
            }
        }
        return false;
    }


    /**
     * 인증 제외 URL확인 (특정 패스이하 전부 허용의 경우 [/패스/**], 그이외의 경우 완전일치만 허용)
     * @param urlPattern
     * @param url
     * @return
     */
    public static boolean match (String urlPattern, String url){
        // path 디렉토리이하를 전부 허용한 경우
        if (urlPattern.endsWith("/**")){
            String match = urlPattern.substring(0, urlPattern.lastIndexOf("**"));
            String matchEqual = urlPattern.substring(0, urlPattern.lastIndexOf("/**"));
            return url.startsWith(match) || url.equals(matchEqual);
        } else {
            // 단일패스 허용
            if (org.springframework.util.StringUtils.pathEquals(urlPattern, url)){
                return true;
            }
        }
        return false;
    }

    /**
     * validate체크결과를 반환
     * @param field 에러 필드
     * @param messageCode 메세지 코드
     * @return
     */
    public static List<ValidateErrorResponse> validateResponse(String field, String messageCode) {
        return List.of(new ValidateErrorResponse(field, messageCode));
    }

}
