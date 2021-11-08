/**
 * 树洞信
 */
package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuHoleNoteViewEntity")
public class BiuHoleNoteViewEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private long id;
    @EntityProperty(comment = "用户ID")
    private long userId = 0;
    @EntityProperty(comment = "内容")
    private String content = "";
    @EntityProperty(comment = "是否私有 0-否 1-是")
    private int isPrivate = 0;
    @EntityProperty(comment = "是否匿名显示 0-否 1-是")
    private int nickShow = 0;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;
    private String labels = "";
    private String moods = "";
    private long commentNum = 0;
    private long favorNum = 0;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public int getNickShow() {
        return nickShow;
    }

    public void setNickShow(int nickShow) {
        this.nickShow = nickShow;
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

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getMoods() {
        return moods;
    }

    public void setMoods(String moods) {
        this.moods = moods;
    }

    public long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(long commentNum) {
        this.commentNum = commentNum;
    }

    public long getFavorNum() {
        return favorNum;
    }

    public void setFavorNum(long favorNum) {
        this.favorNum = favorNum;
    }
}
