package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuUserEntity")
public class BiuUserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public static final int USER_AVAIL_STATUS = 1;
    public static final int USER_INVAIL_STATUS = 0;

    private long id;
    @EntityProperty(comment = "名称")
    private String username = "";
    @EntityProperty(comment = "昵称")
    private String nick = "";
    @EntityProperty(comment = "联系电话")
    private String phone = "";
    @EntityProperty(comment = "联系邮箱")
    private String email = "";
    @EntityProperty(comment = "所在省份")
    private long province = 0;
    @EntityProperty(comment = "所在城市")
    private long city = 0;
    @EntityProperty(comment = "所在区县")
    private long country = 0;
    @EntityProperty(comment = "所在街道")
    private long street = 0;
    @EntityProperty(comment = "具体地址")
    private String address = "";
    @EntityProperty(comment = "邮编")
    private String zipcode = "";
    @EntityProperty(comment = "最后登录IP")
    private String lastIp = "";
    @EntityProperty(comment = "备注")
    private String remark = "";
    @EntityProperty(comment = "使用状态 1-正常 0-禁用")
    private int use_status = 0;
    @EntityProperty(comment = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;
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

    public long getProvince() {
        return province;
    }

    public void setProvince(long province) {
        this.province = province;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }

    public long getStreet() {
        return street;
    }

    public void setStreet(long street) {
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

    public int getUse_status() {
        return use_status;
    }

    public void setUse_status(int use_status) {
        this.use_status = use_status;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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
}
