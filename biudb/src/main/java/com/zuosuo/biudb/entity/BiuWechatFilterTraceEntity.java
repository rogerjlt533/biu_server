package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuWechatFilterTraceEntity")
public class BiuWechatFilterTraceEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private long id;
    @EntityProperty(comment = "过滤类型 1-多媒体 2-文字")
    private int filterType = 0;
    @EntityProperty(comment = "用户ID")
    private long userId = 0;
    @EntityProperty(comment = "使用类别 1-音频 2-图片 1-树洞信 2-树洞信评论 3-消息")
    private int useType = 0;
    @EntityProperty(comment = "任务ID")
    private String traceId = "";
    @EntityProperty(comment = "图片ID")
    private long imageId = 0;
    @EntityProperty(comment = "树洞ID")
    private long noteId = 0;
    @EntityProperty(comment = "评论ID")
    private long commentId = 0;
    @EntityProperty(comment = "消息ID")
    private long messageId = 0;
    @EntityProperty(comment = "是否危险 0-否 1-是")
    private int containRisk = 0;
    @EntityProperty(comment = "综合结果")
    private String result = "";
    @EntityProperty(comment = "详细结果")
    private String detail = "";
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

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
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

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getContainRisk() {
        return containRisk;
    }

    public void setContainRisk(int containRisk) {
        this.containRisk = containRisk;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
