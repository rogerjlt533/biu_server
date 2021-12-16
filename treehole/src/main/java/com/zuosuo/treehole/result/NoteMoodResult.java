package com.zuosuo.treehole.result;

public class NoteMoodResult {
    private String code, emoj, tag;
    private int checked;

    public String getCode() {
        return code != null ? code.trim() : "";
    }

    public void setCode(String code) {
        this.code = code;
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
