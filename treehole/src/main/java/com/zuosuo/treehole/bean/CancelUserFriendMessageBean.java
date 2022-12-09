package com.zuosuo.treehole.bean;

public class CancelUserFriendMessageBean extends BaseVerifyBean {

    private String messageId;

    public String getMessageId() {
        return messageId != null ? messageId.trim() : "";
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public VerifyResult verify() {
        if (getMessageId().isEmpty()) {
            return new VerifyResult("消息ID不能为空");
        }
        return new VerifyResult();
    }
}
