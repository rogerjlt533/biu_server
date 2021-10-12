package com.zuosuo.mybatis.provider;

public enum CheckStatusEnum {
    // check status 0-all 1-normal 2-deleted
    ALL(0), NORMAL(1), DELETED(2);

    private int value;

    private CheckStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
