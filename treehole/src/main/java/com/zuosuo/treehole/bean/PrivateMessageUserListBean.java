package com.zuosuo.treehole.bean;

public class PrivateMessageUserListBean extends BaseVerifyBean {

    private int isFriend, read;

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
        if (getRead() < 0 || getRead() > 2) {
            return new VerifyResult("请选择正确的阅读状态");
        }
        return new VerifyResult();
    }
}
