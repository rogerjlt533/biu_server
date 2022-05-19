package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.RemoveUserFriendMessageBean;
import com.zuosuo.treehole.bean.VerifyResult;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class RemoveUserFriendMessageAction extends BaseAction {

    private RemoveUserFriendMessageBean bean;
    private UserProcessor userProcessor;

    public RemoveUserFriendMessageAction(HttpServletRequest request, RemoveUserFriendMessageBean bean, UserProcessor userProcessor) {
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
        long friendId = bean.getFriend().isEmpty() ? 0 : userProcessor.decodeHash(bean.getFriend());
        long messageId = bean.getMessageId().isEmpty() ? 0 : userProcessor.decodeHash(bean.getMessageId());
        if (messageId > 0) {
            userProcessor.removeUserFriendSingleMessage(getLoginInfoBean().getUserId(), messageId);
        } else if(friendId > 0) {
            userProcessor.removeUserFriendSession(getLoginInfoBean().getUserId(), friendId);
        }
        return JsonResult.success();
    }
}
