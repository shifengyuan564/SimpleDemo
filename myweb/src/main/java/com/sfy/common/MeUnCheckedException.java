package com.sfy.common;

/**
 * Description: 自定义运行时异常
 * Author: shifengyuan@jd.com
 * Date: 2016/1/22
 */
public class MeUnCheckedException extends RuntimeException {

    private String errCode;


    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        String msg = MeExceptionCode.getDescByCode(errCode);
        return msg != null ? msg : getMessage();
    }

    public MeUnCheckedException(MeExceptionCode meExptCode, Throwable e) {
        super(meExptCode.getDesc(), e);
        this.errCode = meExptCode.getCode();
    }

    public MeUnCheckedException(String errCode) {
        super();
        this.errCode = errCode;
    }

    public MeUnCheckedException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public MeUnCheckedException(String message, Throwable cause, String errCode) {
        super(message, cause);
        this.errCode = errCode;
    }

    public MeUnCheckedException(String errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
    }

}
