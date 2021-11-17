package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserInfoBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * 更新用户个人信息
 */
public class UpdateInfoAction extends BaseAction {

    private UserInfoBean bean;
    private UserProcessor userProcessor;

    public UpdateInfoAction(HttpServletRequest request, UserInfoBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        FuncResult loginResult = userProcessor.updateInfo(getLoginInfoBean().getUserId(), bean);
        if (!loginResult.isStatus()) {
            return new JsonResult(loginResult.getMessage());
        }
        return JsonResult.success();
    }
}