package com.zuosuo.treehole.bean;

import com.zuosuo.biudb.entity.BiuHoleNoteEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateNoteBean extends BaseVerifyBean {

    public static final String CREATE = "create";
    public static final String EDIT = "edit";

    private String method, id, content, images, mood;
    private long label;
    private int isSelf, nick;

    public String getMethod() {
        return method != null ? method.trim() : "";
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content != null ? content.trim() : "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        String images = this.images == null ? "" : this.images.replaceAll(" ", "");
        if (images.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(images.trim().split(","));
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public int getIsSelfRealValue() {
        // 私有值与数据库反向
        return isSelf > 0 ? BiuHoleNoteEntity.PRIVATE_NO : BiuHoleNoteEntity.PRIVATE_YES;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public int getNick() {
        return nick;
    }

    public int getNickRealValue() {
        return getNick() > 0 ? BiuHoleNoteEntity.NICK_NO : BiuHoleNoteEntity.NICK_YES;
    }

    public void setNick(int nick) {
        this.nick = nick;
    }

    public String getMood() {
        return mood != null ? mood.trim() : "";
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public long getLabel() {
        return label;
    }

    public void setLabel(long label) {
        this.label = label;
    }

    @Override
    public VerifyResult verify() {
        if (!Arrays.asList(CREATE, EDIT).contains(getMethod())) {
            return new VerifyResult("请选择正确的操作类型");
        }
        if (getMethod().equals(EDIT) && getId().isEmpty()) {
            return new VerifyResult("请选择树洞信息");
        }
        if (getContent().isEmpty() && getImages().isEmpty()) {
            return new VerifyResult("请输入树洞内容");
        }
        if (getIsSelfRealValue() == BiuHoleNoteEntity.PRIVATE_YES && getNick() == BiuHoleNoteEntity.NICK_YES) {
            return new VerifyResult("私有内容不可设置昵称显示");
        }
        return new VerifyResult();
    }
}
