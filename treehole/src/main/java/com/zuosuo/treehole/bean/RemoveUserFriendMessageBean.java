package com.zuosuo.treehole.bean;

public class RemoveUserFriendMessageBean extends BaseVerifyBean {

    private String friend, messageId;

    public String getFriend() {
        return friend != null ? friend.trim() : "";
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getMessageId() {
        return messageId != null ? messageId.trim() : "";
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public VerifyResult verify() {
        if (getFriend().isEmpty() && getMessageId().isEmpty()) {
            return new VerifyResult("关联信息不能为空");
        }
        return new VerifyResult();
    }
}
