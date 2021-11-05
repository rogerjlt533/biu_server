package com.zuosuo.treehole.task;

import java.util.Date;

public class UserCollectInput {
    private long userId;
    private long relateId;
    private Date time;

    public UserCollectInput(long userId, long relateId, Date time) {
        this.userId = userId;
        this.relateId = relateId;
        this.time = time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
