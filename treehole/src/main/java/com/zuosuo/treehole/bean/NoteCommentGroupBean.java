package com.zuosuo.treehole.bean;

import java.util.Arrays;

public class NoteCommentGroupBean extends BaseVerifyBean {

    private String note, comment_id, last, orderby;
    private int mine;

    public String getNote() {
        return note != null ? note.trim() : "";
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComment_id() {
        return comment_id != null ? comment_id.trim() : "";
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public int getMine() {
        return mine;
    }

    public void setMine(int mine) {
        this.mine = mine;
    }

    public String getLast() {
        return last != null ? last.trim() : "";
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getOrderby() {
        return orderby == null ? "asc" : orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    @Override
    public VerifyResult verify() {
        if (getComment_id().isEmpty() && getNote().isEmpty()) {
            return new VerifyResult("您未选择对应树洞消息");
        }
        if (!Arrays.asList("asc", "desc").contains(getOrderby())) {
            return new VerifyResult("排序顺序不匹配");
        }
        return new VerifyResult();
    }
}
