package com.zuosuo.biudb.config;

public enum BiuTableEnum {
    USER("biu_users"), AREA("biu_areas"), INTEREST("biu_interests"), MESSAGE("biu_messages"), HOLE_NOTE("biu_hole_notes");

    private String value;

    private BiuTableEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
