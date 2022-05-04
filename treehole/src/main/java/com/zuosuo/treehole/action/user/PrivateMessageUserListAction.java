package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.PrivateMessageUserListBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PrivateMessageUserListAction extends BaseAction {

    private UserProcessor userProcessor;
    private PrivateMessageUserListBean bean;

    public PrivateMessageUserListAction(HttpServletRequest request, PrivateMessageUserListBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult getResult = userProcessor.getFriendMessageUserList(getLoginInfoBean().getUserId(), bean.getIsFriend(), bean.getRead());
        return JsonDataResult.success((Map) getResult.getResult());
    }
}
