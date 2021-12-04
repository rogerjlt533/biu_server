package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.CancelBlackBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class CancelBlackAction extends BaseAction {

    private CancelBlackBean bean;
    private UserProcessor userProcessor;

    public CancelBlackAction(HttpServletRequest request, CancelBlackBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        long relateId = bean.getRelate().isEmpty() ? 0 : userProcessor.decodeUserHash(bean.getRelate());
        userProcessor.cancelBlackUser(getLoginInfoBean().getUserId(), relateId);
        return JsonResult.success();
    }
}
