package com.zuosuo.treehole.bean;

import java.util.Arrays;

public class NoteCommentListBean extends BaseVerifyBean {

    private String note, last, orderby;

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

    public String getOrderby() {
        return orderby == null ? "asc" : orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    @Override
    public VerifyResult verify() {
        if (getNote().isEmpty()) {
            return new VerifyResult("您未选择对应树洞消息");
        }
        if (!Arrays.asList("asc", "desc").contains(getOrderby())) {
            return new VerifyResult("排序顺序不匹配");
        }
        return new VerifyResult();
    }
}
