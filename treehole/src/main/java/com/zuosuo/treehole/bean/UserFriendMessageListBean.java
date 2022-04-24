package com.zuosuo.treehole.bean;

public class UserFriendMessageListBean extends BaseVerifyBean {

    private String friend;

    public String getFriend() {
        return friend != null ? friend.trim() : "";
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    @Override
    public VerifyResult verify() {
        return new VerifyResult();
    }
}
