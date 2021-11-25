package com.zuosuo.treehole.result;

public class MyInfoSingleResult<T> {

    private String tag;
    private T value;

    public MyInfoSingleResult() {
        tag = "";
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
