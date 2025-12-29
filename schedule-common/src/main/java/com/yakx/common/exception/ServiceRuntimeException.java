package com.yakx.common.exception;

public class ServiceRuntimeException extends RuntimeException {

    private int code;
    private String message;
    private Error error;

    public ServiceRuntimeException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceRuntimeException(Error error) {
        super(error.getJson());
        this.error = error;
    }

    public int getCode() {
        if (this.error != null) {
            return error.getCode();
        } else {
            return code;
        }
    }

    public String message() {
        if (this.error != null) {
            return error.getMsg();
        } else {
            return this.message;
        }
    }

    public Error getWxError() {
        return this.error;
    }
}
