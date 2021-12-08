package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class UserResult {
    private String id = "", name = "", title = "", introduce = "", desc = "", sortTime = "";
    private int isCollect = 0, communicates;
    private List<String> images, interests;

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

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public int getCommunicates() {
        return communicates;
    }

    public void setCommunicates(int communicates) {
        this.communicates = communicates;
    }

    public void addImage(String file) {
        images.add(file);
    }

    public void addInterest(String interest) {
        interests.add(interest);
    }
}
