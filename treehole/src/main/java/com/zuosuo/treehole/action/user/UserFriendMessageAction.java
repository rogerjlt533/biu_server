package com.zuosuo.treehole.action.user;

import com.zuosuo.biudb.redis.BiuRedisFactory;
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
    private BiuRedisFactory biuRedisFactory;

    public UserFriendMessageAction(HttpServletRequest request, UserFriendMessageBean bean, UserProcessor userProcessor, BiuRedisFactory biuRedisFactory) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
        this.biuRedisFactory = biuRedisFactory;
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
