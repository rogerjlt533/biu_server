package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserInitUpdateInfoBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class UserInitUpdateInfoAction extends BaseAction {

    private UserInitUpdateInfoBean bean;
    private UserProcessor userProcessor;

    public UserInitUpdateInfoAction(HttpServletRequest request, UserInitUpdateInfoBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        FuncResult loginResult = userProcessor.initUserInfo(getLoginInfoBean().getUserId(), bean);
        if (!loginResult.isStatus()) {
            return new JsonResult(loginResult.getMessage());
        }
        return JsonResult.success();
    }
}
