package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserFriendMessageBean;
import com.zuosuo.treehole.bean.VerifyResult;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class UserFriendMessageAction extends BaseAction {

    private UserFriendMessageBean bean;
    private UserProcessor userProcessor;

    public UserFriendMessageAction(HttpServletRequest request, UserFriendMessageBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        long userId = getLoginInfoBean().getUserId();
        long friendId = bean.getFriend().isEmpty() ? 0 : userProcessor.decodeHash(bean.getFriend());
        FuncResult result = userProcessor.sendUserFriendMessage(userId, friendId, bean);
        if (!result.isStatus()) {
            return new JsonResult(result.getMessage());
        }
        return JsonResult.success();
    }
}
