package org.example.biji_lite.common;

import lombok.Getter;

/**
 * 业务异常，Service 层抛出后由全局异常处理器统一处理
 */
@Getter
public class BusinessException extends RuntimeException {

    /** 错误码，默认 400 */
    private final Integer code;

    public BusinessException(String message) {
        this(400, message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
