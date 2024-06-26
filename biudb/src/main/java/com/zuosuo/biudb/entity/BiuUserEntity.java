package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Calendar;
import java.util.Date;

@Alias("BiuUserEntity")
public class BiuUserEntity extends BaseEntity {

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
    public static final int PRIVATE_MSG_OPEN_STATUS = 1;
    public static final int PRIVATE_MSG_CLOSE_STATUS = 0;
    public static final int USER_SEX_MAN = 1;
    public static final int USER_SEX_WOMEN = 2;
    public static final String LABEL_USER_SEX_MAN = "男";
    public static final String LABEL_USER_SEX_WOMEN = "女";

    private long id;
    @EntityProperty(comment = "用户编号")
    private String userCardno = "";
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
    @EntityProperty(comment = "联系电话")
    private String phone = "";
    @EntityProperty(comment = "联系邮箱")
    private String email = "";
    @EntityProperty(comment = "所在省份")
    private String province = "";
    @EntityProperty(comment = "所在国家")
    private String nation = "";
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
    @EntityProperty(comment = "是否开启私信 0-否 1-是")
    private int priMsgStatus = 0;
    @EntityProperty(comment = "是否发送笔友注册信息 0-否 1-是")
    private int penPubMsg = 0;
    @EntityProperty(comment = "锁定开关状态 0-否 1-是")
    private int lockStatus = 0;
    @EntityProperty(comment = "每日笔友申请次数限制 0-默认3次 其他-对应次数")
    private int applyFriendLimit = 0;
    @EntityProperty(comment = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;
    @EntityProperty(comment = "排序时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sortTime;
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

    public String getUserCardno() {
        return userCardno;
    }

    public void setUserCardno(String userCardno) {
        this.userCardno = userCardno;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
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

    public int getApplyFriendLimit() {
        return applyFriendLimit;
    }

    public void setApplyFriendLimit(int applyFriendLimit) {
        this.applyFriendLimit = applyFriendLimit;
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

    // 计算年龄
    public int getAge() {
        return birthdayYear > 0 ? Calendar.getInstance().get(Calendar.YEAR) - birthdayYear : 0;
    }

    public int getIsPenuser() {
        return isPenuser;
    }

    public void setIsPenuser(int isPenuser) {
        this.isPenuser = isPenuser;
    }

    public int getPriMsgStatus() {
        return priMsgStatus;
    }

    public void setPriMsgStatus(int priMsgStatus) {
        this.priMsgStatus = priMsgStatus;
    }

    public int getPenPubMsg() {
        return penPubMsg;
    }

    public void setPenPubMsg(int penPubMsg) {
        this.penPubMsg = penPubMsg;
    }

    public int getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(int lockStatus) {
        this.lockStatus = lockStatus;
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
                tag = "保密";
        }
        return tag;
    }
}
