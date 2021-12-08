package com.zuosuo.treehole.bean;

import java.util.Arrays;

public class ApplyFriendBean extends BaseVerifyBean {

    public static final String COMMUNICATE = "communicate";
    public static final String APPLY = "apply";
    public static final String PASS = "pass";
    public static final String REFUSE = "refuse";
    public static final String CANCEL = "cancel";

    private String method, friend;
    private int communicate = 0;

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

    public int getCommunicate() {
        return communicate;
    }

    public void setCommunicate(int communicate) {
        this.communicate = communicate;
    }

    @Override
    public VerifyResult verify() {
        String method = getMethod();
        if (method.isEmpty()) {
            return new VerifyResult("操作类型不能为空");
        }
        if (method.equals(ApplyFriendBean.APPLY) && getCommunicate() == 0) {
            return new VerifyResult("通讯方式不能为空");
        }
        if (!Arrays.asList(ApplyFriendBean.APPLY, ApplyFriendBean.PASS, ApplyFriendBean.REFUSE, ApplyFriendBean.COMMUNICATE, ApplyFriendBean.CANCEL).contains(method)) {
            return new VerifyResult("操作类型不匹配");
        }
        if (getFriend().isEmpty()) {
            return new VerifyResult("关联用户不能为空");
        }
        return new VerifyResult();
    }
}
