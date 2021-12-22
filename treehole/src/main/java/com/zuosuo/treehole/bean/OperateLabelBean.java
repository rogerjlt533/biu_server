package com.zuosuo.treehole.bean;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Arrays;

public class OperateLabelBean extends BaseVerifyBean {

    public static final String ADD = "add";
    public static final String REMOVE = "remove";

    private String method, tag;
    private long id;

    public String getMethod() {
        return method != null ? method.trim() : "";
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTag() {
        return tag != null ? tag.trim() : "";
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public VerifyResult verify() {
        if (!Arrays.asList(ADD, REMOVE).contains(getMethod())) {
            return new VerifyResult("操作类型错误");
        }
        if (getMethod().equals(ADD) && getTag().isEmpty()) {
            return new VerifyResult("标签名不能为空");
        }
        if (getMethod().equals(REMOVE) && getId() <= 0) {
            return new VerifyResult("标签不能为空");
        }
        return new VerifyResult();
    }
}
