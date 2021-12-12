package com.zuosuo.treehole.result;

public class NoteLabelResult {
    private String id, tag;
    private int checked;

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
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
