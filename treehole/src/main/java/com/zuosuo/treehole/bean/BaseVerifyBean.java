package com.zuosuo.treehole.bean;

public abstract class BaseVerifyBean {

    private long page = 0;
    private int size = 0;

    public abstract VerifyResult verify();

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
