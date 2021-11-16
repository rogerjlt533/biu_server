/**
 * 用户媒体资源
 */
package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuUserImageEntity")
public class BiuUserImageEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int USE_TYPE_INTRODUCE = 1;
    public static final int USE_TYPE_NOTE = 2;
    public static final int USE_TYPE_NOTE_COMMENT = 3;
    public static final int USE_TYPE_AVATOR = 4;

    private long id;
    @EntityProperty(comment = "用户ID")
    private long userId = 0;
    @EntityProperty(comment = "使用类别 1-用户简介 2-树洞信 3-树洞信评论")
    private int useType = 0;
    @EntityProperty(comment = "文件地址")
    private String file = "";
    @EntityProperty(comment = "资源排序")
    private int sortIndex = 0;
    @EntityProperty(comment = "资源关联记录")
    private int relateId = 0;
    @EntityProperty(comment = "文件hash")
    private String hashCode = "";
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public int getRelateId() {
        return relateId;
    }

    public void setRelateId(int relateId) {
        this.relateId = relateId;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
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
