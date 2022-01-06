package com.zuosuo.treehole.config;

public enum SystemOption {
    APP_KEY("base64:nOOymAy124XnwY74oG6MMs/cT98xeR7t5Z5P6PsQu0E="),
    TOKEN_DAYS("30"),
    USER_IMAGE("http://devimage.fang-cun.net/upload/tou.png"),
    REGISTER_NOTICE("欢迎加入BIU笔友，记录生活，畅聊感悟，分享密语心情，搜寻您的云中笔友吧！"),
    REGISTER_NOTICE_PLAT("亲爱的笔友，您可在BIU笔友发布自己的任何想法和感受，但不包括以下内容:\n" +
            "1）政治敏感\n" +
            "2）涉黄\n" +
            "3）侮辱，谩骂，攻击其他用户\n" +
            "4）广告\n" +
            "5）引导交友\n" +
            "6）恶意刷屏\n" +
            "7）封建迷信\n" +
            "8）恶意消极评论\n" +
            "9）引导交易\n" +
            "\n" +
            "在发布图片时还需要注意一下类型图片，BIU会根据审核，予以判定是否显示:\n" +
            "1）自拍身材类（例如胸肌，腹肌，锁骨等容易引起误会的图片）\n" +
            "2）各类二维码\n" +
            "3）黑丝，制服，萝莉风\n" +
            "4）血腥，暴力，恐怖，色情等一系列令人不适的图片\n" +
            "如发现以上严禁内容会视其严重程度，BIU平台将给予不同程度的禁言或冻结账号惩罚。");

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
