package com.zuosuo.treehole.bean;

public class UserInitUpdateInfoBean {
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

    public boolean verify() {
        if (nick == null) {
            return false;
        }
        if (nick.trim().isEmpty()) {
            return false;
        }
        if (image == null) {
            return false;
        }
        if (image.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
