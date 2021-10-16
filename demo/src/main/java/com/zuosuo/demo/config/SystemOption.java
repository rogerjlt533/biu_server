package com.zuosuo.demo.config;

public enum SystemOption {
    APP_KEY("base64:nOOymAy124XnwY74oG6MMs/cT98xeR7t5Z5P6PsQu0E="),
    // TODO: 测试
    APP_URL("http://meishitest.wonmore.com/"),
    TOKEN_DAYS("2");

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
