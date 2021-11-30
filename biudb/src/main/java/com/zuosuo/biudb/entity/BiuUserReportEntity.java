package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuUserReportEntity")
public class BiuUserReportEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int REPORT_TYPE_RECOMMEND = 1;
    public static final int REPORT_TYPE_TOUSU = 2;
    public static final int REPORT_TYPE_OTHER = 3;
    public static final int REPORT_TYPE_REPORT = 4;

    private long id;
    @EntityProperty(comment = "类型 1-建议 2-举报 3-其他")
    private int reportType = 0;
    @EntityProperty(comment = "用户ID")
    private long userId = 0;
    @EntityProperty(comment = "被举报用户ID")
    private long relateId = 0;
    @EntityProperty(comment = "内容")
    private String content = "";
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

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRelateId() {
        return relateId;
    }

    public void setRelateId(long relateId) {
        this.relateId = relateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
