package com.zuosuo.treehole.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoBean extends BaseVerifyBean {

    private String penName, image, phone, email = "", zipcode = "", title, introduce, address, province, city, country, street, images, search_sexes, communicates, search_communicates, interests;
    private int sex, birthdayYear, startAge, endAge;

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

    public String getPhone() {
        return phone == null ? "" : phone.trim();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email == null ? "" : email.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode == null ? "" : zipcode.trim();
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce == null ? "" : introduce.trim();
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddress() {
        return address == null ? "" : address.trim();
    }

    public void setAddress(String address) {
        this.address = address;
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
        if (images == null) {
            return new ArrayList<>();
        }
        if (images.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(image.trim().split(","));
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<Integer> getSearch_sexes() {
        if (search_sexes == null) {
            return new ArrayList<>();
        }
        if (search_sexes.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(search_sexes.trim().split(",")).stream().map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());
    }

    public void setSearch_sexes(String search_sexes) {
        this.search_sexes = search_sexes;
    }

    public List<Integer> getCommunicates() {
        if (communicates == null) {
            return new ArrayList<>();
        }
        if (communicates.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(communicates.trim().split(",")).stream().map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());
    }

    public void setCommunicates(String communicates) {
        this.communicates = communicates;
    }

    public List<Integer> getSearch_communicates() {
        if (search_communicates == null) {
            return new ArrayList<>();
        }
        if (search_communicates.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(search_communicates.trim().split(",")).stream().map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());
    }

    public void setSearch_communicates(String search_communicates) {
        this.search_communicates = search_communicates;
    }

    public List<Long> getInterests() {
        if (interests == null) {
            return new ArrayList<>();
        }
        if (interests.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(interests.trim().split(",")).stream().map(item -> Long.parseLong(item.trim())).collect(Collectors.toList());
    }

    public void setInterests(String interests) {
        this.interests = interests;
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

    @Override
    public VerifyResult verify() {
        if (penName == null) {
            return new VerifyResult("请填写笔名");
        }
        if (penName.trim().isEmpty()) {
            return new VerifyResult("请填写笔名");
        }
        if (communicates == null) {
            return new VerifyResult("未选择通讯方式");
        }
        if (communicates.trim().isEmpty()) {
            return new VerifyResult("未选择通讯方式");
        }
        return new VerifyResult();
    }
}
