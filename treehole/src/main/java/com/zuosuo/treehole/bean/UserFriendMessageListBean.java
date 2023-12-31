package com.zuosuo.treehole.bean;

public class UserFriendMessageListBean extends BaseVerifyBean {

    private String friend;
    private int isFriend, read;

    public String getFriend() {
        return friend != null ? friend.trim() : "";
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public VerifyResult verify() {
        if (getFriend().isEmpty()) {
            return new VerifyResult("缺少用户信息");
        }
        if (getRead() < 0 || getRead() > 2) {
            return new VerifyResult("请选择正确的阅读状态");
        }
        return new VerifyResult();
    }
}
