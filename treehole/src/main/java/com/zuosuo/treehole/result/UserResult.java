package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class UserResult {
    private String id = "", name = "", introduce = "", province = "", sex = "", sortTime = "";
    private int age = 0, useEmail = 0, useLetter = 0;
    private List<String> images, interests;
    private List<Integer> communicates;

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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSortTime() {
        return sortTime;
    }

    public void setSortTime(String sortTime) {
        this.sortTime = sortTime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUseEmail() {
        return useEmail;
    }

    public void setUseEmail(int useEmail) {
        this.useEmail = useEmail;
    }

    public int getUseLetter() {
        return useLetter;
    }

    public void setUseLetter(int useLetter) {
        this.useLetter = useLetter;
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

    public List<Integer> getCommunicates() {
        return communicates;
    }

    public void setCommunicates(List<Integer> communicates) {
        this.communicates = communicates;
    }

    public void addImage(String file) {
        images.add(file);
    }

    public void addInterest(String interest) {
        interests.add(interest);
    }

    public void addCommunicate(int communicate) {
        communicates.add(communicate);
    }
}
