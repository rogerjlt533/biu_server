package com.zuosuo.treehole.bean;

public class UserReadBean extends BaseVerifyBean {

    private String relate;

    public String getRelate() {
        return relate != null ? relate.trim() : "";
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    @Override
    public VerifyResult verify() {
        if (getRelate().isEmpty()) {
            return new VerifyResult("请选择关联用户");
        }
        return new VerifyResult();
    }
}
