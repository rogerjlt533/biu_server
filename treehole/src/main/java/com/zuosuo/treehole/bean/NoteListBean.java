package com.zuosuo.treehole.bean;

import java.util.Arrays;

public class NoteListBean extends BaseVerifyBean {

    public static final String INDEX = "index";
    public static final String MINE = "mine";
    public static final String FRIEND = "friend";

    private String method, last;

    public String getMethod() {
        return method != null ? method.trim() : "";
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLast() {
        return last != null ? last.trim() : "";
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public VerifyResult verify() {
        if (!Arrays.asList(INDEX, MINE, FRIEND).contains(getMethod())) {
            return new VerifyResult("请选择正确的查询类型");
        }
        return new VerifyResult();
    }
}
