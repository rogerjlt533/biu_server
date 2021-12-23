package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class UserResult {
    private String id = "", name = "", image = "", title = "", introduce = "", desc = "", sortTime = "";
    private int isCollect = 0, communicate, isRead;
    private List<String> images;
    private List<UserInterestResult> interests;

    public UserResult() {
        images = new ArrayList<>();
        interests = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSortTime() {
        return sortTime;
    }

    public void setSortTime(String sortTime) {
        this.sortTime = sortTime;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<UserInterestResult> getInterests() {
        return interests;
    }

    public void setInterests(List<UserInterestResult> interests) {
        this.interests = interests;
    }

    public int getCommunicate() {
        return communicate;
    }

    public void setCommunicate(int communicate) {
        this.communicate = communicate;
    }

    public void addImage(String file) {
        images.add(file);
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
