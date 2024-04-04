package net.telos.cmmn.web;

import net.telos.cmmn.utils.MessageUtils;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 공통 응답 클래스
 *
 * @param <T>
 */
@Data
@Component

public class BaseResponse<T> {

	private String code;

	private String message ;

	private List<ValidateErrorResponse> validate;

	private T data;

	public BaseResponse(){
		this.code = BaseResponseCode.SUCCESS;
	}

	public BaseResponse(T data) {
		this.code = BaseResponseCode.SUCCESS;
		this.data = data;
	}

	public BaseResponse(String code) {
		this.code = code;
	}

	public BaseResponse(String code, String messageKey) {
		this.code = code;
		this.message = MessageUtils.getMessage(messageKey);
	}

	public BaseResponse(String code, T data) {
		this(code);
		this.data = data;
	}

	public BaseResponse(List<ValidateErrorResponse> validate) {
		this.code = BaseResponseCode.VALIDATE_ERROR;
		this.validate = validate;
	}

	public BaseResponse(T data, List<ValidateErrorResponse> validate) {
		this.code = BaseResponseCode.VALIDATE_ERROR;
		this.data = data;
		this.validate = validate;
	}

}
