package com.yakx.ezviz.exception;

import com.yakx.common.exception.ServiceException;

public class EzvizException  extends ServiceException {

    public EzvizException(String message, int code) {
        super(message, code);
    }
}
