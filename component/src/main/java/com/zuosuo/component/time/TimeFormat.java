package com.zuosuo.component.time;

public enum TimeFormat {
    DEFAULT_DATETIME("yyyy-MM-dd HH:mm:ss"), DEFAULT_DATE("yyyy-MM-dd");

    private String value;

    private TimeFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
