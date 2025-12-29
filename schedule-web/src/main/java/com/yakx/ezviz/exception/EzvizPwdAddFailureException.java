package com.yakx.ezviz.exception;

public class EzvizPwdAddFailureException extends EzvizException {

    public EzvizPwdAddFailureException() {
        super("添加临时密码失败，请联系管理员！", 20601);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
