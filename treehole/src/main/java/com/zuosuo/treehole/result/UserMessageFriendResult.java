package com.zuosuo.treehole.result;

public class UserMessageFriendResult {

    public static final int ALLOW_AUTH = 1;
    public static final int NOT_ALLOW_AUTH = 0;
    public static final int ENABLE = 1;
    public static final int UNENABLE = 0;

    private String id, user, friend, name, desc;
    private int allowAuth, status;

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user != null ? user.trim() : "";
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFriend() {
        return friend != null ? friend.trim() : "";
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getName() {
        return name != null ? name.trim() : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc != null ? desc.trim() : "";
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAllowAuth() {
        return allowAuth;
    }

    public void setAllowAuth(int allowAuth) {
        this.allowAuth = allowAuth;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
