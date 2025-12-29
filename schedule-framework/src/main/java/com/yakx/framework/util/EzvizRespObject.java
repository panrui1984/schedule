package com.yakx.framework.util;

import lombok.Data;

@Data
public class EzvizRespObject <T>{

    private String msg;

    private String code;

    private T data;
}
