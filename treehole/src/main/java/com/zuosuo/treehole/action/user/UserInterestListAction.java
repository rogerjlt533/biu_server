package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户爱好列表
 */
public class UserInterestListAction extends BaseAction {

    private UserProcessor userProcessor;

    public UserInterestListAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult getResult = userProcessor.getUserInterestList(getLoginInfoBean().getUserId());
        if (!getResult.isStatus()) {
            return new JsonDataResult<>(getResult.getMessage());
        }
        return JsonDataResult.success((Map) getResult.getResult());
    }
}
