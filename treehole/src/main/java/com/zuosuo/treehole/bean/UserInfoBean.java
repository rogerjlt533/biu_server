package com.zuosuo.treehole.bean;

import com.zuosuo.biudb.entity.BiuUserCommunicateEntity;
import com.zuosuo.biudb.entity.BiuUserEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoBean extends BaseVerifyBean {

    private String method, nick, image, penName, username, phone, email, zipcode, title, introduce, address, province, city, country, street, images, search_sexes, communicates, search_communicates, interests;
    private int sex, birthdayYear, startAge, endAge;

    public List<String> getMethod() {
        String method = this.method == null ? "" : this.method.replaceAll(" ", "");
        if (method.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(method.trim().split(","));
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPenName() {
        return penName == null ? "" : penName.trim();
    }

    public String getNick() {
        return nick == null ? "" : nick.trim();
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImage() {
        return image == null ? "" : image.trim();
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getUsername() {
        return username == null ? "" : username.trim();
    }

    public void setUsername(String username) {
        this.username = username;
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
        return street == null ? "" : street.trim();
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<String> getImages() {
        String images = this.images == null ? "" : this.images.replaceAll(" ", "");
        if (images.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(images.trim().split(","));
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<Integer> getSearch_sexes() {
        String search_sexes = this.search_sexes == null ? "3" : this.search_sexes.replaceAll(" ", "");
        search_sexes = search_sexes.isEmpty() ? "3" : search_sexes;
        if (search_sexes.isEmpty()) {
            return new ArrayList<>();
        }
        if (search_sexes.equals("3")) {
            search_sexes = String.join(",", String.valueOf(BiuUserEntity.USER_SEX_MAN), String.valueOf(BiuUserEntity.USER_SEX_WOMEN));
        }
        return Arrays.asList(search_sexes.trim().split(",")).stream().map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());
    }

    public void setSearch_sexes(String search_sexes) {
        this.search_sexes = search_sexes;
    }

    public List<Integer> getCommunicates() {
        String communicates = this.communicates == null ? "3" : this.communicates.replaceAll(" ", "");
        communicates = communicates.isEmpty() ? "3" : communicates;
        if (communicates.isEmpty()) {
            return new ArrayList<>();
        }
        if (communicates.equals("3")) {
            communicates = String.join(",", String.valueOf(BiuUserCommunicateEntity.COM_METHOD_LETTER), String.valueOf(BiuUserCommunicateEntity.COM_METHOD_EMAIL));
        }
        return Arrays.asList(communicates.trim().split(",")).stream().map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());
    }

    public void setCommunicates(String communicates) {
        this.communicates = communicates;
    }

    public List<Integer> getSearch_communicates() {
        String search_communicates = this.search_communicates == null ? "3" : this.search_communicates.replaceAll(" ", "");
        search_communicates = search_communicates.isEmpty() ? "3" : search_communicates;
        if (search_communicates.isEmpty()) {
            return new ArrayList<>();
        }
        if (search_communicates.equals("3")) {
            search_communicates = String.join(",", String.valueOf(BiuUserCommunicateEntity.COM_METHOD_LETTER), String.valueOf(BiuUserCommunicateEntity.COM_METHOD_EMAIL));
        }
        return Arrays.asList(search_communicates.trim().split(",")).stream().map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());
    }

    public void setSearch_communicates(String search_communicates) {
        this.search_communicates = search_communicates;
    }

    public List<Long> getInterests() {
        String interests = this.interests == null ? "" : this.interests.replaceAll(" ", "");
        if (interests.isEmpty()) {
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
        if (getMethod().isEmpty()) {
            return new VerifyResult("请选择方法参数");
        }
        return new VerifyResult();
    }
}
