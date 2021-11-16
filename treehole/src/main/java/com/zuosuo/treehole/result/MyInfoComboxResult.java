package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class MyInfoComboxResult<T> {

    private String tag;
    private List<T> list;

    public MyInfoComboxResult() {
        tag = "";
        list = new ArrayList<>();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
