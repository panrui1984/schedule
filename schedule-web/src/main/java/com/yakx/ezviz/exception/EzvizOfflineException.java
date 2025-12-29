package com.yakx.ezviz.exception;

public class EzvizOfflineException extends EzvizException {

    public EzvizOfflineException() {
        super("设备不在线，请联系管理员！", 20007);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
