package com.zuosuo.treehole.bean;

public class UserInitUpdateInfoBean extends BaseVerifyBean {
    private String nick;
    private String image;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public VerifyResult verify() {
        if (nick == null) {
            return new VerifyResult("用户昵称不能为空");
        }
        if (nick.trim().isEmpty()) {
            return new VerifyResult("用户昵称不能为空");
        }
        if (image == null) {
            return new VerifyResult("用户头像不能为空");
        }
        if (image.trim().isEmpty()) {
            return new VerifyResult("用户头像不能为空");
        }
        return new VerifyResult();
    }
}
