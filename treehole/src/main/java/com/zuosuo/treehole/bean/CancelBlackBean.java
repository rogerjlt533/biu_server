package com.zuosuo.treehole.bean;

public class CancelBlackBean extends BaseVerifyBean {

    private String relate;

    public String getRelate() {
        return relate == null ? "" : relate.trim();
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    @Override
    public VerifyResult verify() {
        if (getRelate().isEmpty()) {
            return new VerifyResult("关联用户不能为空");
        }
        return new VerifyResult();
    }
}
