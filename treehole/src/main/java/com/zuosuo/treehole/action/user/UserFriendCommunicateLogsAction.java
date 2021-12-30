package com.zuosuo.treehole.action.user;

import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserFriendCommunicateLogBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class UserFriendCommunicateLogsAction extends BaseAction {

    private UserFriendCommunicateLogBean bean;
    private UserProcessor userProcessor;

    public UserFriendCommunicateLogsAction(HttpServletRequest request, UserFriendCommunicateLogBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }


    @Override
    public Object run() {
        return null;
    }
}
