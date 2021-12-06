package com.zuosuo.treehole.bean;

import java.util.Arrays;

public class SignCommunicateBean extends BaseVerifyBean {

    public static final String SEND = "send";
    public static final String RECEIVE = "receive";

    private String log, friend, method;

    public String getLog() {
        return log == null ? "" : log.trim();
    }

    public void setLog(String log) {
        this.log = log;
    }

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
        if (!Arrays.asList(SignCommunicateBean.SEND, SignCommunicateBean.RECEIVE).contains(method)) {
            return new VerifyResult("操作类型不匹配");
        }
        if (method.equals(SignCommunicateBean.RECEIVE) && getLog().isEmpty()) {
            return new VerifyResult("通讯记录不能为空");
        }
        if (method.equals(SignCommunicateBean.SEND) && getFriend().isEmpty()) {
            return new VerifyResult("好友信息不能为空");
        }
        return new VerifyResult();
    }
}
