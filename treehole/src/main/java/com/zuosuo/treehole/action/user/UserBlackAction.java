package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserBlackBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class UserBlackAction extends BaseAction {

    private UserBlackBean bean;
    private UserProcessor userProcessor;

    public UserBlackAction(HttpServletRequest request, UserBlackBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        long relateId = bean.getRelate().isEmpty() ? 0 : userProcessor.decodeHash(bean.getRelate());
        userProcessor.blackUser(getLoginInfoBean().getUserId(), relateId);
        return JsonResult.success();
    }
}
