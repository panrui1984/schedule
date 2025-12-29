package com.yakx.ezviz.exception;

public class EzvizNetworkException extends EzvizException {

    public EzvizNetworkException() {
        super("设备连接网络异常，请联系管理员！", 20006);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
