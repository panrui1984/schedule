package com.yakx.ezviz.exception;

public class EzvizPwdReachLimitException extends EzvizException {

    public EzvizPwdReachLimitException() {
        super("密码达到最大限制，请联系管理员！", 20600);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
