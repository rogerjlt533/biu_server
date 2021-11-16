package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Calendar;
import java.util.Date;

@Alias("BiuUserViewEntity")
public class BiuUserViewEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int USER_NOT_PEN = 0;
    public static final int USER_IS_PEN = 1;
    public static final int USER_AVAIL_STATUS = 1;
    public static final int USER_INVAIL_STATUS = 0;
    public static final int COMMUNICATE_OPEN_STATUS = 1;
    public static final int COMMUNICATE_CLOSE_STATUS = 0;
    public static final int SEARCH_OPEN_STATUS = 1;
    public static final int SEARCH_CLOSE_STATUS = 0;
    public static final int ANONYMOUS_OPEN_STATUS = 1;
    public static final int ANONYMOUS_CLOSE_STATUS = 0;
    public static final int USER_SEX_MAN = 1;
    public static final int USER_SEX_WOMEN = 2;

    private long id;
    @EntityProperty(comment = "名称")
    private String username = "";
    @EntityProperty(comment = "昵称")
    private String nick = "";
    @EntityProperty(comment = "用户头像")
    private String image = "";
    @EntityProperty(comment = "笔名")
    private String penName = "";
    @EntityProperty(comment = "openid")
    private String openid = "";
    @EntityProperty(comment = "unionid")
    private String unionid = "";
    @EntityProperty(comment = "性别 1-男 2-女")
    private int sex = 0;
    @EntityProperty(comment = "匹配起始年龄(周岁)")
    private int matchStartAge;
    @EntityProperty(comment = "匹配终止年龄(周岁)")
    private int matchEndAge = 0;
    @EntityProperty(comment = "出生年份")
    private int birthdayYear = 0;
    private int age = 0;
    @EntityProperty(comment = "联系电话")
    private String phone = "";
    @EntityProperty(comment = "联系邮箱")
    private String email = "";
    @EntityProperty(comment = "所在省份")
    private String province = "";
    @EntityProperty(comment = "所在城市")
    private String city = "";
    @EntityProperty(comment = "所在区县")
    private String country = "";
    @EntityProperty(comment = "所在街道")
    private String street = "";
    @EntityProperty(comment = "具体地址")
    private String address = "";
    @EntityProperty(comment = "邮编")
    private String zipcode = "";
    @EntityProperty(comment = "用户简介标题")
    private String title = "";
    @EntityProperty(comment = "用户简介")
    private String introduce = "";
    @EntityProperty(comment = "最后登录IP")
    private String lastIp = "";
    @EntityProperty(comment = "备注")
    private String remark = "";
    @EntityProperty(comment = "使用状态 1-正常 0-禁用")
    private int useStatus = 0;
    @EntityProperty(comment = "树洞评论开关状态 1-开启 0-关闭")
    private int commentStatus = 0;
    @EntityProperty(comment = "寻友开关状态 1-开启 0-关闭")
    private int searchStatus = 0;
    @EntityProperty(comment = "匿名状态 1-开启 0-关闭")
    private int anonymous = 0;
    @EntityProperty(comment = "是否笔友 0-否 1-是")
    private int isPenuser = 0;
    private int collectNum = 0;
    @EntityProperty(comment = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;
    @EntityProperty(comment = "排序时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sortTime;
    private String selfInterest = "";
    private String searchInterest = "";
    private String selfCommunicate = "";
    private String searchCommunicate = "";
    private String searchSex = "";
    private String protectedUser = "";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getMatchStartAge() {
        return matchStartAge;
    }

    public void setMatchStartAge(int matchStartAge) {
        this.matchStartAge = matchStartAge;
    }

    public int getMatchEndAge() {
        return matchEndAge;
    }

    public void setMatchEndAge(int matchEndAge) {
        this.matchEndAge = matchEndAge;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(int birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getSortTime() {
        return sortTime;
    }

    public void setSortTime(Date sortTime) {
        this.sortTime = sortTime;
    }

    public String getSelfInterest() {
        return selfInterest;
    }

    public void setSelfInterest(String selfInterest) {
        this.selfInterest = selfInterest;
    }

    public String getSearchInterest() {
        return searchInterest;
    }

    public void setSearchInterest(String searchInterest) {
        this.searchInterest = searchInterest;
    }

    public String getSelfCommunicate() {
        return selfCommunicate;
    }

    public void setSelfCommunicate(String selfCommunicate) {
        this.selfCommunicate = selfCommunicate;
    }

    public String getSearchCommunicate() {
        return searchCommunicate;
    }

    public void setSearchCommunicate(String searchCommunicate) {
        this.searchCommunicate = searchCommunicate;
    }

    public String getSearchSex() {
        return searchSex;
    }

    public void setSearchSex(String searchSex) {
        this.searchSex = searchSex;
    }

    public String getProtectedUser() {
        return protectedUser;
    }

    public void setProtectedUser(String protectedUser) {
        this.protectedUser = protectedUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getIsPenuser() {
        return isPenuser;
    }

    public void setIsPenuser(int isPenuser) {
        this.isPenuser = isPenuser;
    }

    // 性别标签
    public String getSexTag() {
        String tag;
        switch (sex) {
            case USER_SEX_MAN:
                tag = BiuUserEntity.LABEL_USER_SEX_MAN;
                break;
            case USER_SEX_WOMEN:
                tag = BiuUserEntity.LABEL_USER_SEX_WOMEN;
                break;
            default:
                tag = "";
        }
        return tag;
    }
}
