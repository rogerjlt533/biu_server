package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserHomeBean;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.result.UserHomeResult;

import javax.servlet.http.HttpServletRequest;

public class UserHomeAction extends BaseAction {

    private UserHomeBean bean;
    private UserProcessor userProcessor;

    public UserHomeAction(HttpServletRequest request, UserHomeBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<UserHomeResult> run() {
//        bean.setId(userProcessor.encodeHash(9));
//        getLoginInfoBean().setUserId(175);
        long userId = userProcessor.decodeHash(bean.getId());
        FuncResult getResult = userProcessor.getUserHomeInfo(userId, getLoginInfoBean().getUserId());
        if (!getResult.isStatus()) {
            return new JsonDataResult<>(getResult.getMessage());
        }
        return JsonDataResult.success((UserHomeResult) getResult.getResult());
    }
}
