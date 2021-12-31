package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class UserMessageResult {
    private int messageType, readStatus, useFriendApply;
    private String id, note, messageTag, title, content, showTime;
    private UserMessageFriendResult friendApply;
    private List<String> images;

    public UserMessageResult() {
        friendApply = new UserMessageFriendResult();
        images = new ArrayList<>();
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

    public String getNote() {
        return note != null ? note.trim() : "";
    }

    public void setNote(String note) {
        this.note = note;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
