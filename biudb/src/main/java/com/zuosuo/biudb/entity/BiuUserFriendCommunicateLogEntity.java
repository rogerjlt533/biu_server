package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuUserFriendCommunicateLogEntity")
public class BiuUserFriendCommunicateLogEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private long id;
    @EntityProperty(comment = "好友记录ID")
    private long friendId = 0;
    @EntityProperty(comment = "好友通讯方式记录ID")
    private long comRid = 0;
    @EntityProperty(comment = "通信方式 1-信件 2-E-mail")
    private int communicateType = 0;
    @EntityProperty(comment = "发起人")
    private long sendUser = 0;
    @EntityProperty(comment = "接收人")
    private long receiveUser = 0;
    @EntityProperty(comment = "是否确认收到 0-否 1-是")
    private int receiveStatus = 0;
    @EntityProperty(comment = "签收时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;
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

    public long getComRid() {
        return comRid;
    }

    public void setComRid(long comRid) {
        this.comRid = comRid;
    }

    public int getCommunicateType() {
        return communicateType;
    }

    public void setCommunicateType(int communicateType) {
        this.communicateType = communicateType;
    }

    public long getSendUser() {
        return sendUser;
    }

    public void setSendUser(long sendUser) {
        this.sendUser = sendUser;
    }

    public long getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(long receiveUser) {
        this.receiveUser = receiveUser;
    }

    public int getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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
