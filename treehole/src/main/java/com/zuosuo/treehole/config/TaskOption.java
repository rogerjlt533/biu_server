package com.zuosuo.treehole.config;

public enum TaskOption {

    USER_COLLECT("biu:user:collect"), USER_CANCEL("biu:user:collect:cancel"), USER_INDEX_SYNC("biu:user:index:sync");

    private String value;

    private TaskOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getInteger() {
        return Integer.valueOf(value);
    }
}
