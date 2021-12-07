package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class UserHomeResult {

    private String id, name, desc, title, introduce, province, sex, age, collectTag, friendTag;
    private List<String> images;
    private MyInfoSingleResult<Integer> communicate;
    private List<UserInterestResult> interests;
    private int collect, black, friend, allowCollect, allowFriend, cancelFriend;

    public UserHomeResult() {
        images = new ArrayList<>();
        communicate = new MyInfoSingleResult<>();
        interests = new ArrayList<>();
    }

    public String getId() {
        return id != null? id.trim(): "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name != null? name.trim(): "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc != null? desc.trim(): "";
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title != null? title.trim(): "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce != null? introduce.trim(): "";
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getProvince() {
        return province != null? province.trim(): "";
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCollectTag() {
        return collectTag != null? collectTag.trim(): "";
    }

    public void setCollectTag(String collectTag) {
        this.collectTag = collectTag;
    }

    public String getFriendTag() {
        return friendTag != null? friendTag.trim(): "";
    }

    public void setFriendTag(String friendTag) {
        this.friendTag = friendTag;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getSex() {
        return sex != null? sex.trim(): "";
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age != null? age.trim(): "";
    }

    public void setAge(String age) {
        this.age = age;
    }

    public MyInfoSingleResult<Integer> getCommunicate() {
        return communicate;
    }

    public void setCommunicate(MyInfoSingleResult<Integer> communicate) {
        this.communicate = communicate;
    }

    public List<UserInterestResult> getInterests() {
        return interests;
    }

    public void setInterests(List<UserInterestResult> interests) {
        this.interests = interests;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public int getFriend() {
        return friend;
    }

    public void setFriend(int friend) {
        this.friend = friend;
    }

    public int getAllowCollect() {
        return allowCollect;
    }

    public void setAllowCollect(int allowCollect) {
        this.allowCollect = allowCollect;
    }

    public int getAllowFriend() {
        return allowFriend;
    }

    public void setAllowFriend(int allowFriend) {
        this.allowFriend = allowFriend;
    }

    public int getCancelFriend() {
        return cancelFriend;
    }

    public void setCancelFriend(int cancelFriend) {
        this.cancelFriend = cancelFriend;
    }
}
