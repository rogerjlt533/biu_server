package com.zuosuo.wechat;

public class AccessTokenInfo {
    private String accessToken;
    private int expiresIn;

    public AccessTokenInfo() {
        accessToken = "";
        expiresIn = 0;
    }

    public AccessTokenInfo(String accessToken, int expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
