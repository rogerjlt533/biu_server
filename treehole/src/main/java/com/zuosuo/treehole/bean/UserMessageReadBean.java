package com.zuosuo.treehole.bean;

public class UserMessageReadBean extends BaseVerifyBean {

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
            return new VerifyResult("请选择消息");
        }
        return new VerifyResult();
    }
}
