package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class UserHomeResult {

    private String id, name, desc, image, title, introduce, province, zipcode, sex, age, friendHash, collectTag, friendTag;
    private List<String> images;
    private MyInfoSingleResult<Integer> communicate;
    private List<UserInterestResult> interests;
    private int self, collect, black, friend, allowCollect, allowFriend, cancelFriend, rollFriend, allowOperateApply, priMsgStatus;

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

    public String getImage() {
        return image != null ? image.trim() : "";
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public String getFriendHash() {
        return friendHash;
    }

    public void setFriendHash(String friendHash) {
        this.friendHash = friendHash;
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

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
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

    public int getRollFriend() {
        return rollFriend;
    }

    public void setRollFriend(int rollFriend) {
        this.rollFriend = rollFriend;
    }

    public int getAllowOperateApply() {
        return allowOperateApply;
    }

    public void setAllowOperateApply(int allowOperateApply) {
        this.allowOperateApply = allowOperateApply;
    }

    public int getPriMsgStatus() {
        return priMsgStatus;
    }

    public void setPriMsgStatus(int priMsgStatus) {
        this.priMsgStatus = priMsgStatus;
    }
}
