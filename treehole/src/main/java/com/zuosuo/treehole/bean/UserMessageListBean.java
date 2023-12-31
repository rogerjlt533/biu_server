package com.zuosuo.treehole.bean;

import com.zuosuo.biudb.entity.BiuMessageEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMessageListBean extends BaseVerifyBean {

    public static final String NOTICE = "notice";
    public static final String PUBLIC = "public";
    public static final String MESSAGE = "message";
    public static final String PRIVATE = "private";

    private String type;
    private int sub, read;

    public String getType() {
        return type != null ? type.trim() : "";
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSub() {
        return sub;
    }

    public void setSub(int sub) {
        this.sub = sub;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public List<Integer> subList() {
        List<Integer> list = new ArrayList<>();
        if (getType().equals(NOTICE)) {
            list.add(BiuMessageEntity.NOTICE_APPLY);
            list.add(BiuMessageEntity.NOTICE_FRIEND);
            list.add(BiuMessageEntity.NOTICE_SEND);
            list.add(BiuMessageEntity.NOTICE_RECEIVE);
        } else if(getType().equals(MESSAGE)) {
            list.add(BiuMessageEntity.MESSAGE_COMMENT);
            list.add(BiuMessageEntity.MESSAGE_FAVOR);
            list.add(BiuMessageEntity.MESSAGE_REPLY);
        } else if(getType().equals(PUBLIC)) {
            list.add(BiuMessageEntity.PUBLIC_NOTICE);
            list.add(BiuMessageEntity.PUBLIC_ACTIVE);
            list.add(BiuMessageEntity.PUBLIC_UPDATE);
        } else if(getType().equals(PRIVATE)) {
            list.add(BiuMessageEntity.PRIVATE_MESSAGE);
        }
        return list;
    }

    @Override
    public VerifyResult verify() {
        if (!Arrays.asList(NOTICE, MESSAGE, PUBLIC, PRIVATE).contains(getType())) {
            return new VerifyResult("请选择类型");
        }
        if (getSub() > 0  && !subList().contains(getSub())) {
            return new VerifyResult("请选择正确的子类别");
        }
        if (getRead() < 0 || getRead() > 2) {
            return new VerifyResult("请选择正确的阅读状态");
        }
        return new VerifyResult();
    }
}
