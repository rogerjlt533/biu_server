package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserReadBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class UserReadAction extends BaseAction {

    private UserReadBean bean;
    private UserProcessor userProcessor;

    public UserReadAction(HttpServletRequest request, UserReadBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        long relateId = userProcessor.decodeHash(bean.getRelate());
        FuncResult getResult = userProcessor.readUser(getLoginInfoBean().getUserId(), relateId);
        if (!getResult.isStatus()) {
            return new JsonResult(getResult.getMessage());
        }
        return JsonResult.success();
    }
}
