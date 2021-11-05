package com.zuosuo.treehole.bean;

public class LoginCodeBean extends BaseVerifyBean {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public VerifyResult verify() {
        if (code == null) {
            return new VerifyResult("用户code不能为空");
        }
        if (code.trim().isEmpty()) {
            return new VerifyResult("用户code不能为空");
        }
        return new VerifyResult();
    }
}
