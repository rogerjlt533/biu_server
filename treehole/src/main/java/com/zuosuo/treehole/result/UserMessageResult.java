package com.zuosuo.treehole.result;

public class UserMessageResult {
    private int messageType, readStatus, useFriendApply;
    private String id, messageTag, title, content, showTime;
    private UserMessageFriendResult friendApply;

    public UserMessageResult() {
        friendApply = new UserMessageFriendResult();
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public int getUseFriendApply() {
        return useFriendApply;
    }

    public void setUseFriendApply(int useFriendApply) {
        this.useFriendApply = useFriendApply;
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageTag() {
        return messageTag != null ? messageTag.trim() : "";
    }

    public void setMessageTag(String messageTag) {
        this.messageTag = messageTag;
    }

    public String getTitle() {
        return title != null ? title.trim() : "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content != null ? content.trim() : "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShowTime() {
        return showTime != null ? showTime.trim() : "";
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public UserMessageFriendResult getFriendApply() {
        return friendApply;
    }

    public void setFriendApply(UserMessageFriendResult friendApply) {
        this.friendApply = friendApply;
    }
}
