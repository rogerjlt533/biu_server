package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.response.ResponseConfig;
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
        bean.setFriend(userProcessor.encodeUserHash(9));
        long friendId = bean.getFriend().isEmpty() ? 0 : userProcessor.decodeUserHash(bean.getFriend());
        FuncResult processResult = userProcessor.processFriend(getLoginInfoBean().getUserId(), friendId, bean);
        if (!processResult.isStatus()) {
            return new JsonResult(processResult.getMessage());
        }
        if (bean.getMethod().equals(ApplyFriendBean.COMMUNICATE)) {
            return new JsonDataResult<>(ResponseConfig.SUCCESS_CODE, "", processResult.getResult());
        }
        return JsonResult.success();
    }
}