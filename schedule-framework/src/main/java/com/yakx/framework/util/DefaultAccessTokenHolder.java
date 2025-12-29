package com.yakx.framework.util;

public class DefaultAccessTokenHolder extends AccessTokenHolder {

    private AccessToken accessToken;

    public DefaultAccessTokenHolder(){
        super();
    }

    @Override
    public synchronized AccessToken getAccessToken() {
       // if (accessToken == null || accessToken.expired()) {
       //     refreshToken();
       // }
        //
        refreshToken();
        return accessToken;
    }

    @Override
    public synchronized void refreshToken() {
        System.out.println("fetch access token");
        String content = fetchAccessToken();
        System.out.println(content);
        this.accessToken = AccessToken.fromJson(content);

    }

    @Override
    public void expireToken() {
        accessToken.setExpireTime(-30);//强制设置为无效
    }

}
