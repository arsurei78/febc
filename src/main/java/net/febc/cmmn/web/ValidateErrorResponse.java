package net.febc.cmmn.web;

import net.febc.cmmn.utils.MessageUtils;
import lombok.Data;

/**
 * validate체크 에러시
 */
@Data
public class ValidateErrorResponse {

    private String field;
    private String message;
    public ValidateErrorResponse(String field, String message) {
        this.field = field;
        this.message = MessageUtils.getMessage(message);
    }

    public ValidateErrorResponse(String field, String message, Object[] ar) {
        this.field = field;
        this.message = MessageUtils.getMessage(message, ar);
    }
}
