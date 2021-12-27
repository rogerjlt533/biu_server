package com.zuosuo.treehole.config;

public enum SystemOption {
    APP_KEY("base64:nOOymAy124XnwY74oG6MMs/cT98xeR7t5Z5P6PsQu0E="),
    TOKEN_DAYS("30"),
    USER_IMAGE("http://devimage.fang-cun.net/upload/tou.png");

    private String value;

    private SystemOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getInteger() {
        return Integer.valueOf(value);
    }
}
