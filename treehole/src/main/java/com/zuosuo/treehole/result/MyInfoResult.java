package com.zuosuo.treehole.result;

import java.util.ArrayList;
import java.util.List;

public class MyInfoResult {
    private String id = "", desc = "", nick = "", cardno = "", username = "", penName = "", sexTag = "", image = "", title = "", introduce = "", address = "", phone = "", email = "", zipcode = "", street = "";
    private AreaInfoResult province, city, country;
    private int sex = 0, birthdayYear = 0, startAge = 0, endAge = 0, useStatus = 0, commentStatus = 0, searchStatus = 0, isPenuser = 0;
    private List<String> images;
    private MyInfoSingleResult<Integer> communicates, searchCommunicates, searchSexes;
    private MyInfoComboxResult<UserInterestResult> interests;

    public MyInfoResult() {
        province = new AreaInfoResult();
        city = new AreaInfoResult();
        country = new AreaInfoResult();
        images = new ArrayList<>();
        communicates = new MyInfoSingleResult<>();
        searchCommunicates = new MyInfoSingleResult<>();
        searchSexes = new MyInfoSingleResult<>();
        interests = new MyInfoComboxResult<>();
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc != null ? desc.trim() : "";
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getSexTag() {
        return sexTag;
    }

    public void setSexTag(String sexTag) {
        this.sexTag = sexTag;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public AreaInfoResult getProvince() {
        return province;
    }

    public void setProvince(AreaInfoResult province) {
        this.province = province;
    }

    public AreaInfoResult getCity() {
        return city;
    }

    public void setCity(AreaInfoResult city) {
        this.city = city;
    }

    public AreaInfoResult getCountry() {
        return country;
    }

    public void setCountry(AreaInfoResult country) {
        this.country = country;
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

    public MyInfoSingleResult<Integer> getCommunicates() {
        return communicates;
    }

    public void setCommunicates(MyInfoSingleResult<Integer> communicates) {
        this.communicates = communicates;
    }

    public MyInfoSingleResult<Integer> getSearchCommunicates() {
        return searchCommunicates;
    }

    public void setSearchCommunicates(MyInfoSingleResult<Integer> searchCommunicates) {
        this.searchCommunicates = searchCommunicates;
    }

    public MyInfoSingleResult<Integer> getSearchSexes() {
        return searchSexes;
    }

    public void setSearchSexes(MyInfoSingleResult<Integer> searchSexes) {
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
