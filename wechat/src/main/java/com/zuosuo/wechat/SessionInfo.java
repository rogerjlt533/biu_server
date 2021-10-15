package com.zuosuo.wechat;

public class SessionInfo {
    private String openid;
    private String sessionKey;
    private String unionid;
    private int errcode;
    private String errmsg;

    public SessionInfo(int errcode, String errmsg, String openid, String sessionKey, String unionid) {
        this.openid = openid;
        this.sessionKey = sessionKey;
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
