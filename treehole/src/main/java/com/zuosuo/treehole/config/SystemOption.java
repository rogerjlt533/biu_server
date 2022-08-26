package com.zuosuo.treehole.config;

public enum SystemOption {
    APP_KEY("base64:nOOymAy124XnwY74oG6MMs/cT98xeR7t5Z5P6PsQu0E="),
    TOKEN_DAYS("30"),
    USER_IMAGE("http://biuimage.juqihui.cn/upload/tou.png"),
    REGISTER_NOTICE("欢迎加入BIU笔友，记录生活，畅聊感悟，分享密语心情，搜寻您的云中笔友吧！"),
    REGISTER_NOTICE_BANNER("http://biuimage.juqihui.cn/banner_welcome.jpg"),
    REGISTER_NOTICE_IMAGE("http://biuimage.juqihui.cn/welcome.jpg"),
    REGISTER_NOTICE_PLAT_BANNER("http://biuimage.juqihui.cn/banner_notice.jpg"),
    REGISTER_NOTICE_PLAT_IMAGE("http://biuimage.juqihui.cn/noticenew.jpg"),
    REGISTER_NOTICE_PLAT("亲爱的笔友，您可在BIU笔友发布自己的任何想法和感受，但不包括以下内容:<br>" +
            "1）政治敏感<br>" +
            "2）涉黄<br>" +
            "3）侮辱，谩骂，攻击其他用户<br>" +
            "4）广告<br>" +
            "5）引导交友<br>" +
            "6）恶意刷屏<br>" +
            "7）封建迷信<br>" +
            "8）恶意消极评论<br>" +
            "9）引导交易<br>" +
            "<br>" +
            "在发布图片时还需要注意一下类型图片，BIU会根据审核，予以判定是否显示:<br>" +
            "1）自拍身材类（例如胸肌，腹肌，锁骨等容易引起误会的图片）<br>" +
            "2）各类二维码<br>" +
            "3）黑丝，制服，萝莉风<br>" +
            "4）血腥，暴力，恐怖，色情等一系列令人不适的图片<br>" +
            "如发现以上严禁内容会视其严重程度，BIU平台将给予不同程度的禁言或冻结账号惩罚。"),
    APPLY_TITLE("您正在向笔友【#NAME#】提交笔友申请"),
    FAVOR_TITLE("笔友【#NAME#】点赞了你的树洞信"),
    COMMENT_TITLE("笔友【#NAME#】评论了您的树洞信"),
    COMMENT_REPLY_TITLE("笔友【#NAME#】回复了您的消息"),
    SEND_MAIL_TITLE("笔友【#NAME#】已寄出邮件"),
    RECEIVE_MAIL_TITLE("笔友【#NAME#】已接受信件"),
    FRIEND_PASS_TITLE("您已与【#NAME#】成为笔友，快写信给Ta吧"),
    FRIEND_REFUSE_TITLE("笔友【#NAME#】已谢绝您的好友申请"),
    RECEIVE_APPLY_TITLE("笔友【#NAME#】申请与您成为笔友"),
    PRIVATE_MESSAGE_FRIEND_LIMIT("user:private:message:#DATE#:#USERID#:#FRIENDID#"),
    USER_OPENID_KEY("user:openid:#OPENID#"),
    MINI_ACCESSS_TOKEN_KEY("mini:access_token:#APPID#");

    private String value;

    private SystemOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getInteger() {
        return Integer.valueOf(value);
    }
}
