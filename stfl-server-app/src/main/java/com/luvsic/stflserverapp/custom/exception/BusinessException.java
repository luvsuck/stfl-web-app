package com.luvsic.stflserverapp.custom.exception;

import com.luvsic.stflserverapp.custom.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.Serial;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 18:39
 * @description
 */
@Setter
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3852760922322183249L;

    @Getter
    private int code;
    private String message;

    public BusinessException() {
        this(ResultCodeEnum.FAILED);
    }

    public BusinessException(ResultCodeEnum failure) {
        this.code = failure.getCode();
        this.message = failure.getMsg();
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        if (StringUtils.hasText(this.message))
            return this.message;
        return super.getMessage();
    }

    public BusinessException(ResultCodeEnum failure, String additionalInfo) {
        this.code = failure.getCode();
        this.message = failure.getMsg() + "," + additionalInfo;
    }
}
