package com.zuosuo.treehole.bean;

import com.zuosuo.mybatis.tool.PageTool;

public abstract class BaseVerifyBean {

    private long page = 0;
    private int size = 0;

    public abstract VerifyResult verify();

    public long getPage() {
        return page > 0 ? page : 1;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public int getSize() {
        return size <= 0 ? PageTool.DEFAULT_SIZE : size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getOffset() {
        long page = getPage() > 0 ? getPage() : 1;
        return (page - 1) * getSize();
    }
}
