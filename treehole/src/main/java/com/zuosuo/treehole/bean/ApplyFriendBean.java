package com.zuosuo.treehole.bean;

import java.util.Arrays;

public class ApplyFriendBean extends BaseVerifyBean {

    public static final String APPLY = "apply";
    public static final String PASS = "pass";
    public static final String REFUSE = "refuse";

    private String method, friend;

    public String getMethod() {
        return method == null ? "" : method.trim();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFriend() {
        return friend == null ? "" : friend.trim();
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    @Override
    public VerifyResult verify() {

        String method = getMethod();
        if (method.isEmpty()) {
            return new VerifyResult("操作类型不能为空");
        }
        if (!Arrays.asList(ApplyFriendBean.APPLY, ApplyFriendBean.PASS, ApplyFriendBean.REFUSE).contains(method)) {
            return new VerifyResult("操作类型不匹配");
        }
        if (getFriend().isEmpty()) {
            return new VerifyResult("关联用户不能为空");
        }
        return new VerifyResult();
    }
}
