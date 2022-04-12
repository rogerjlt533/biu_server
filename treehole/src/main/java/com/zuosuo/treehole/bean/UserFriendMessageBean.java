package com.zuosuo.treehole.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserFriendMessageBean extends BaseVerifyBean {

    private String friend, images, content;

    public String getFriend() {
        return friend != null ? friend.trim() : "";
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public List<String> getImages() {
        List<String> list = null;
        if (images == null) {
            list = new ArrayList<>();
        } else {
            list = Arrays.asList(images.split(",")).stream().collect(Collectors.toList());
        }
        return list;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getContent() {
        return content != null ? content.trim() : "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public VerifyResult verify() {
        if (getFriend().isEmpty()) {
            return new VerifyResult("好友关系为空");
        }
        if (getContent().isEmpty() && images.isEmpty()) {
            return new VerifyResult("好友消息内容为空");
        }
        return new VerifyResult();
    }
}
