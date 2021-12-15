package com.zuosuo.treehole.bean;

public class NoteCommentListBean extends BaseVerifyBean {

    private String note, last;

    public String getNote() {
        return note != null ? note.trim() : "";
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLast() {
        return last != null ? last.trim() : "";
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public VerifyResult verify() {
        if (getNote().isEmpty()) {
            return new VerifyResult("您未选择对应树洞消息");
        }
        return new VerifyResult();
    }
}
