package com.zuosuo.treehole.config;

import com.zuosuo.wechat.TemplateInfo;

public enum TemplateOption implements TemplateInfo {
    WAITING_PRIVATE_MESSAGE_TEMPLATE("EljBY6FjqxlJo2FSM7RCSGqGHFLHwEFTOQ-gVqdwHZ4", "未读私信通知", 1),
    NEW_COMMENT_TEMPLATE("Odpltd2r3MTRROfUadtWqVxrbgPTaIJfADQM0QQAUYo", "新的评论通知", 1),
    FRIEND_APPLY_TEMPLATE("RXNEOamyG6VCFskTqgpO9-LEX5LiajLitIhUeryNYNg", "新好友申请通知", 1),
    LETTER_REPLY_TEMPLATE("qEa1IwHdRbxyEXegS-eJ0dte6rYKRP1nGebNXBVMUj0", "信件回复提醒", 1);

    private String id;
    private String desc;
    // 1-一次性通知 2-长期通知
    private int type;

    private TemplateOption(String id, String desc, int type) {
        this.id = id;
        this.desc = desc;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public int getType() {
        return type;
    }
}
