package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class MyInfoResult {
    private String penName = "", image = "", title = "", introduce = "", address = "";
    private MyInfoAreaResult province, city, country, street;
    private int sex = 0, birthdayYear = 0, startAge = 0, endAge = 0, useStatus = 0, commentStatus = 0, searchStatus = 0, isPenuser = 0;
    private List<String> images;
    private MyInfoComboxResult<Integer> communicates, searchCommunicates, searchSexes;
    private MyInfoComboxResult<UserInterestResult> interests;

    public MyInfoResult() {
        province = new MyInfoAreaResult();
        city = new MyInfoAreaResult();
        country = new MyInfoAreaResult();
        street = new MyInfoAreaResult();
        images = new ArrayList<>();
        communicates = new MyInfoComboxResult<>();
        searchCommunicates = new MyInfoComboxResult<>();
        searchSexes = new MyInfoComboxResult<>();
        interests = new MyInfoComboxResult<>();
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MyInfoAreaResult getProvince() {
        return province;
    }

    public void setProvince(MyInfoAreaResult province) {
        this.province = province;
    }

    public MyInfoAreaResult getCity() {
        return city;
    }

    public void setCity(MyInfoAreaResult city) {
        this.city = city;
    }

    public MyInfoAreaResult getCountry() {
        return country;
    }

    public void setCountry(MyInfoAreaResult country) {
        this.country = country;
    }

    public MyInfoAreaResult getStreet() {
        return street;
    }

    public void setStreet(MyInfoAreaResult street) {
        this.street = street;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public MyInfoComboxResult<Integer> getCommunicates() {
        return communicates;
    }

    public void setCommunicates(MyInfoComboxResult<Integer> communicates) {
        this.communicates = communicates;
    }

    public MyInfoComboxResult<Integer> getSearchCommunicates() {
        return searchCommunicates;
    }

    public void setSearchCommunicates(MyInfoComboxResult<Integer> searchCommunicates) {
        this.searchCommunicates = searchCommunicates;
    }

    public MyInfoComboxResult<Integer> getSearchSexes() {
        return searchSexes;
    }

    public void setSearchSexes(MyInfoComboxResult<Integer> searchSexes) {
        this.searchSexes = searchSexes;
    }

    public MyInfoComboxResult<UserInterestResult> getInterests() {
        return interests;
    }

    public void setInterests(MyInfoComboxResult<UserInterestResult> interests) {
        this.interests = interests;
    }

    public int getIsPenuser() {
        return isPenuser;
    }

    public void setIsPenuser(int isPenuser) {
        this.isPenuser = isPenuser;
    }
}
