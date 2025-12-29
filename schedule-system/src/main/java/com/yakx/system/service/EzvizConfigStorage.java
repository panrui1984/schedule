package com.yakx.system.service;

public interface EzvizConfigStorage {

    String getAccessToken();

    boolean isAccessTokenExpired();

}
