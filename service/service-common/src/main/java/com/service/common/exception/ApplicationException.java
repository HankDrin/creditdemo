package com.service.common.exception;

import com.service.common.enums.code.BizCodeMsgEnum;
import lombok.Getter;
import lombok.ToString;

/**
 * 应用基础异常
 *
 */
@Getter
@ToString
public class ApplicationException extends RuntimeException {

    private final String errorCode;
    private final String errorMsg;

    public ApplicationException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(String message, String errorCode, String errorMsg) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(String message, Throwable cause, String errorCode, String errorMsg) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(Throwable cause, String errorCode, String errorMsg) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(BizCodeMsgEnum bizCodeMsgEnum) {
        this.errorCode = bizCodeMsgEnum.getCode();
        this.errorMsg = bizCodeMsgEnum.getMsg();
    }
}
