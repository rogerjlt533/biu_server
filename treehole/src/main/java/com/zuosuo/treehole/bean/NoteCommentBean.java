package com.zuosuo.treehole.bean;

import java.util.Arrays;

public class NoteCommentBean extends BaseVerifyBean {

    public static final String NOTE = "note";
    public static final String COMMENT = "comment";

    private String method, note, comment, content;

    public String getMethod() {
        return method != null ? method.trim() : "";
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNote() {
        return note != null ? note.trim() : "";
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComment() {
        return comment != null ? comment.trim() : "";
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content != null ? content.trim() : "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public VerifyResult verify() {
        if (!Arrays.asList(NOTE, COMMENT).contains(getMethod())) {
            return new VerifyResult("操作类型不匹配");
        }
        if (getMethod().equals(NOTE) && getNote().isEmpty()) {
            return new VerifyResult("评论树洞信为空");
        }
        if (getMethod().equals(COMMENT) && getComment().isEmpty()) {
            return new VerifyResult("树洞评论为空");
        }
        if (getContent().isEmpty()) {
            return new VerifyResult("评论内容为空");
        }
        return new VerifyResult();
    }
}
