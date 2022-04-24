package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PrivateMessageUserListAction extends BaseAction {

    private UserProcessor userProcessor;

    public PrivateMessageUserListAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult getResult = userProcessor.getFriendMessageUserList(getLoginInfoBean().getUserId());
        return JsonDataResult.success((Map) getResult.getResult());
    }
}
