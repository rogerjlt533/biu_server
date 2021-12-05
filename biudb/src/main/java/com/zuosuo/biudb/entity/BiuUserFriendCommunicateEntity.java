package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuUserFriendCommunicateEntity")
public class BiuUserFriendCommunicateEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int WAITING_STATUS = 0;
    public static final int PASS_STATUS = 1;
    public static final int REFUSE_STATUS = 2;

    private long id;
    @EntityProperty(comment = "好友记录ID")
    private long friendId = 0;
    @EntityProperty(comment = "通信方式 1-信件 2-E-mail")
    private int communicateType = 0;
    @EntityProperty(comment = "最后的通讯记录ID")
    private long lastLog = 0;
    @EntityProperty(comment = "确认状态 0-待确认 1-是 2-已拒绝")
    private int confirmStatus = 0;
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

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public int getCommunicateType() {
        return communicateType;
    }

    public void setCommunicateType(int communicateType) {
        this.communicateType = communicateType;
    }

    public long getLastLog() {
        return lastLog;
    }

    public void setLastLog(long lastLog) {
        this.lastLog = lastLog;
    }

    public int getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(int confirmStatus) {
        this.confirmStatus = confirmStatus;
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
