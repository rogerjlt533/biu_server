package com.zuosuo.treehole.result;

public class UserInterestResult {
    private long id = 0;
    private int checked = 0, tag_group = 0;
    private String name = "";

    public UserInterestResult(long id, String name, int checked, int tag_group) {
        this.id = id;
        this.name = name;
        this.checked = checked;
        this.tag_group = tag_group;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getTag_group() {
        return tag_group;
    }

    public void setTag_group(int tag_group) {
        this.tag_group = tag_group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
