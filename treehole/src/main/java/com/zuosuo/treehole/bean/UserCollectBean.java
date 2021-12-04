package com.zuosuo.treehole.bean;

import java.util.Arrays;

public class UserCollectBean extends BaseVerifyBean {

    public static final String COLLECT = "collect";
    public static final String CANCEL = "cancel";

    private String method, relate;

    public String getMethod() {
        return method == null ? "" : method.trim();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRelate() {
        return relate == null ? "" : relate.trim();
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    @Override
    public VerifyResult verify() {
        String method = getMethod();
        if (method.isEmpty()) {
            return new VerifyResult("操作类型不能为空");
        }
        if (!Arrays.asList(UserCollectBean.COLLECT, UserCollectBean.CANCEL).contains(method)) {
            return new VerifyResult("操作类型不匹配");
        }
        if (getRelate().isEmpty()) {
            return new VerifyResult("关联用户不能为空");
        }
        return new VerifyResult();
    }
}
