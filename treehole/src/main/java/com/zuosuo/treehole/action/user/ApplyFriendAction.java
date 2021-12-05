package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.ApplyFriendBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class ApplyFriendAction extends BaseAction {

    private ApplyFriendBean bean;
    private UserProcessor userProcessor;

    public ApplyFriendAction(HttpServletRequest request, ApplyFriendBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        long friendId = bean.getFriend().isEmpty() ? 0 : userProcessor.decodeUserHash(bean.getFriend());
        FuncResult loginResult = userProcessor.processFriend(getLoginInfoBean().getUserId(), friendId, bean.getMethod());
        if (!loginResult.isStatus()) {
            return new JsonResult(loginResult.getMessage());
        }
        return JsonResult.success();
    }
}
