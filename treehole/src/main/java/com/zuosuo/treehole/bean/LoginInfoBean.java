package com.zuosuo.treehole.bean;

public class LoginInfoBean {
    private long userId;

    public LoginInfoBean() {
        this.userId = 0;
    }

    public LoginInfoBean(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
