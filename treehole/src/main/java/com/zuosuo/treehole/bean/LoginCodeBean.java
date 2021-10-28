package com.zuosuo.treehole.bean;

public class LoginCodeBean {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean verify() {
        if (code == null) {
            return false;
        }
        if (code.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
