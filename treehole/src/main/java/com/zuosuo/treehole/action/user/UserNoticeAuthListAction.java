package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserNoticeAuthListAction extends BaseAction {

    private UserProcessor userProcessor;

    public UserNoticeAuthListAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<List> run() {
        List list = userProcessor.getNoticeAuthList(0);
        return JsonDataResult.success(list);
    }
}
