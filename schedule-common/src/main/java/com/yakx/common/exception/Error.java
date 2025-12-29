package com.yakx.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yakx.common.utils.JsonMapper;
import lombok.Data;

import java.io.Serializable;

@Data
public class Error implements Serializable {

    @JsonProperty("code")
    private int code;

    @JsonProperty("msg")
    private String msg;
    private String json;

    public static Error fromJson(String json) {
        Error error = JsonMapper.defaultMapper().fromJson(json, Error.class);
        error.setJson(json);
        return error;
    }

}

