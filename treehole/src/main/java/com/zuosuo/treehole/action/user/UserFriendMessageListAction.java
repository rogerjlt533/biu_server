package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserFriendMessageListBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UserFriendMessageListAction extends BaseAction {

    private UserFriendMessageListBean bean;
    private UserProcessor userProcessor;

    public UserFriendMessageListAction(HttpServletRequest request, UserFriendMessageListBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        long friendId = bean.getFriend().isEmpty() ? 0 : userProcessor.decodeHash(bean.getFriend());
        FuncResult getResult = userProcessor.getUserFriendMessageList(getLoginInfoBean().getUserId(), friendId, bean.getPage(), bean.getSize());
        return JsonDataResult.success((Map) getResult.getResult());
    }
}
