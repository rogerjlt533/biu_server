package com.zuosuo.treehole.result;

public class AreaInfoResult {

    private String code, name;

    public AreaInfoResult() {
        code = "";
        name = "";
    }

    public AreaInfoResult(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
