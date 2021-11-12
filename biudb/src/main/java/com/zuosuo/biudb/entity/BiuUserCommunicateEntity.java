/**
 * 用户通讯方式
 */
package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuUserCommunicateEntity")
public class BiuUserCommunicateEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int USE_TYPE_SELF = 1;
    public static final int USE_TYPE_SEARCH = 2;
    public static final int COM_METHOD_LETTER = 1;
    public static final int COM_METHOD_EMAIL = 2;
    public static final String LABEL_COM_METHOD_LETTER = "邮寄信件";
    public static final String LABEL_COM_METHOD_EMAIL = "E-mail";

    private long id;
    @EntityProperty(comment = "用户ID")
    private long userId = 0;
    @EntityProperty(comment = "使用类别 1-自己 2-匹配笔友")
    private int useType = 0;
    @EntityProperty(comment = "通信方式 1-信件 2-E-mail")
    private int comMethod = 0;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public int getComMethod() {
        return comMethod;
    }

    public void setComMethod(int comMethod) {
        this.comMethod = comMethod;
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
