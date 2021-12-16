package com.zuosuo.treehole.result;

public class NoteLabelResult {

    private long id;
    private String tag;
    private int checked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTag() {
        return tag != null ? tag.trim() : "";
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
