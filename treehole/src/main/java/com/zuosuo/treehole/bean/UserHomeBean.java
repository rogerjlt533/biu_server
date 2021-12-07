package com.zuosuo.treehole.bean;

public class UserHomeBean extends BaseVerifyBean {

    private String id;

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public VerifyResult verify() {
        if (getId().isEmpty()) {
            return new VerifyResult("用户不能为空");
        }
        return new VerifyResult();
    }
}
