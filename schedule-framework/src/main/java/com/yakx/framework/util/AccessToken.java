
package com.yakx.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yakx.common.utils.JsonMapper;

public class AccessToken {

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("expireTime")
    private long expireTime;

    private long expiresTill;

    public static AccessToken fromJson(String json) {
        JSONObject object = JSON.parseObject(json);

        return JsonMapper.defaultMapper().fromJson( object.getString("data"), AccessToken.class);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
        this.expiresTill = System.currentTimeMillis() + (expireTime * 1000) - 300000;
    }

    public long getExpiresTill() {
        return expiresTill;
    }

    public boolean expired() {
        return System.currentTimeMillis() > expiresTill;
    }

}