package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class UserGroupInterestListAction extends BaseAction {

    private UserProcessor userProcessor;

    public UserGroupInterestListAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<List> run() {
        List<Map<String, Object>> list = userProcessor.getUserService().getGroupInterestList(getLoginInfoBean().getUserId());
        return JsonDataResult.success(list);
    }
}
