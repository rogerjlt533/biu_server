package com.zuosuo.treehole.result;

public class UserFriendResult {
    private String id, friend, name, image, desc;
    private UserFriendCommunicateInfo communicateInfo;

    public UserFriendResult() {
        communicateInfo = new UserFriendCommunicateInfo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public UserFriendCommunicateInfo getCommunicateInfo() {
        return communicateInfo;
    }

    public void setCommunicateInfo(UserFriendCommunicateInfo communicateInfo) {
        this.communicateInfo = communicateInfo;
    }
}
