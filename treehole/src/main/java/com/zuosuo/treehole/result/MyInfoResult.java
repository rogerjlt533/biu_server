package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class MyInfoResult {
    private String penName = "", title = "", introduce = "", address = "", province = "", city = "", country = "", street = "";
    private int sex = 0, birthdayYear = 0, startAge = 0, endAge = 0, useStatus = 0, commentStatus = 0, searchStatus = 0;
    private List<String> images;
    private List<Long> communicates, searchCommunicates, searchSexes;
    private List<UserInterestResult> interests;

    public MyInfoResult() {
        images = new ArrayList<>();
        communicates = new ArrayList<>();
        searchCommunicates = new ArrayList<>();
        searchSexes = new ArrayList<>();
        interests = new ArrayList<>();
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(int birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public int getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(int useStatus) {
        this.useStatus = useStatus;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public int getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(int searchStatus) {
        this.searchStatus = searchStatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Long> getCommunicates() {
        return communicates;
    }

    public void setCommunicates(List<Long> communicates) {
        this.communicates = communicates;
    }

    public List<Long> getSearchCommunicates() {
        return searchCommunicates;
    }

    public void setSearchCommunicates(List<Long> searchCommunicates) {
        this.searchCommunicates = searchCommunicates;
    }

    public List<Long> getSearchSexes() {
        return searchSexes;
    }

    public void setSearchSexes(List<Long> searchSexes) {
        this.searchSexes = searchSexes;
    }

    public List<UserInterestResult> getInterests() {
        return interests;
    }

    public void setInterests(List<UserInterestResult> interests) {
        this.interests = interests;
    }
}
