package com.zuosuo.treehole.result;

public class NoteMoodResult {
    private String id, emoj, tag;
    private int checked;

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmoj() {
        return emoj != null ? emoj.trim() : "";
    }

    public void setEmoj(String emoj) {
        this.emoj = emoj;
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
