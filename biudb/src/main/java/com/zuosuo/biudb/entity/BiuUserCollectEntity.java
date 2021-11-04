package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuUserCollectEntity")
public class BiuUserCollectEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private long id;
    @EntityProperty(comment = "用户ID")
    private long userId = 0;
    @EntityProperty(comment = "关注用户ID")
    private long relateId = 0;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;
}
