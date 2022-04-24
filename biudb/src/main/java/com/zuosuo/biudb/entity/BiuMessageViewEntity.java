/**
 * 用户消息
 */
package com.zuosuo.biudb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zuosuo.mybatis.annotation.EntityProperty;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("BiuMessageViewEntity")
public class BiuMessageViewEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int READ_WAITING = 0;
    public static final int READ_OK = 1;
    public static final int RELATE_NOTE_TYPE = 1;
    public static final int RELATE_NOTE_COMMENT_TYPE = 2;
    public static final int PUBLIC_NOTICE = 1001;
    public static final int PUBLIC_ACTIVE = 1002;
    public static final int PUBLIC_UPDATE = 1003;
    public static final int MESSAGE_COMMENT = 2001;
    public static final int MESSAGE_FAVOR = 2002;
    public static final int MESSAGE_REPLY = 2003;
    public static final int NOTICE_APPLY= 3001;
    public static final int NOTICE_FRIEND= 3002;
    public static final int NOTICE_SEND= 3003;
    public static final int NOTICE_RECEIVE= 3004;
    public static final int PRIVATE_MESSAGE= 4001;

    private long id;
    @EntityProperty(comment = "目标接收用户ID")
    private long destId = 0;
    @EntityProperty(comment = "来源用户ID")
    private long sourceId = 0;
    @EntityProperty(comment = "消息类型 1-...")
    private int messageType = 0;
    @EntityProperty(comment = "关联记录")
    private long relateId = 0;
    @EntityProperty(comment = "关联类型 1-树洞信 2-树洞信评论")
    private int relateType = 0;
    @EntityProperty(comment = "关联好友记录")
    private long friendId = 0;
    @EntityProperty(comment = "标题")
    private String title = "";
    @EntityProperty(comment = "banner图")
    private String banner = "";
    @EntityProperty(comment = "内容")
    private String content = "";
    @EntityProperty(comment = "阅读状态 0-未读 1-已读")
    private int readStatus = 0;
    @EntityProperty(comment = "内容类型")
    private String contentType = "";
    @EntityProperty(comment = "关联用户")
    private String users = "";
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

    public long getDestId() {
        return destId;
    }

    public void setDestId(long destId) {
        this.destId = destId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public long getRelateId() {
        return relateId;
    }

    public void setRelateId(long relateId) {
        this.relateId = relateId;
    }

    public int getRelateType() {
        return relateType;
    }

    public void setRelateType(int relateType) {
        this.relateType = relateType;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUsers() {
        return users != null ? users : "";
    }

    public void setUsers(String users) {
        this.users = users;
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

    public String getMessageTag() {
        if (messageType == NOTICE_APPLY) {
            return "申请";
        } else if (messageType == NOTICE_FRIEND) {
            return "笔友";
        } else if (messageType == NOTICE_SEND) {
            return "寄信";
        } else if (messageType == NOTICE_RECEIVE) {
            return "收信";
        } else if (messageType == PUBLIC_NOTICE) {
            return "公告";
        } else if (messageType == PUBLIC_ACTIVE) {
            return "活动";
        } else if (messageType == PUBLIC_UPDATE) {
            return "更新";
        } else if (messageType == MESSAGE_COMMENT) {
            return "评论";
        } else if (messageType == MESSAGE_FAVOR) {
            return "点赞";
        } else if (messageType == MESSAGE_REPLY) {
            return "回复";
        } else if (messageType == PRIVATE_MESSAGE) {
            return "私信";
        }
        return "";
    }
}
