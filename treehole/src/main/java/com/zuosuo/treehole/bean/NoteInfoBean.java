package com.zuosuo.treehole.bean;

public class NoteInfoBean extends BaseVerifyBean {

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
            return new VerifyResult("请选择消息记录");
        }
        return new VerifyResult();
    }
}
