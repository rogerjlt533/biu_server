package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserCollectBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class UserCollectAction extends BaseAction {

    private UserCollectBean bean;
    private UserProcessor userProcessor;

    public UserCollectAction(HttpServletRequest request, UserCollectBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        long relateId = userProcessor.decodeUserHash(bean.getRelate());
        FuncResult loginResult = userProcessor.collect(getLoginInfoBean().getUserId(), relateId, bean.getMethod());
        if (!loginResult.isStatus()) {
            return new JsonResult(loginResult.getMessage());
        }
        return JsonResult.success();
    }
}
