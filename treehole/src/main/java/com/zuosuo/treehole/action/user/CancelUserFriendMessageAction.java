package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.CancelUserFriendMessageBean;
import com.zuosuo.treehole.bean.VerifyResult;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class CancelUserFriendMessageAction extends BaseAction {

    private CancelUserFriendMessageBean bean;
    private UserProcessor userProcessor;

    public CancelUserFriendMessageAction(HttpServletRequest request, CancelUserFriendMessageBean bean, UserProcessor userProcessor) {
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
        long messageId = bean.getMessageId().isEmpty() ? 0 : userProcessor.decodeHash(bean.getMessageId());
        FuncResult result = userProcessor.cancelUserFriendMessage(userId, messageId);
        if (!result.isStatus()) {
            return new JsonResult(result.getMessage());
        }
        return JsonResult.success();
    }
}
