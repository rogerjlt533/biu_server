package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserStatusBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户状态修改
 */
public class UserStatusAction extends BaseAction {

    private UserStatusBean bean;
    private UserProcessor userProcessor;

    public UserStatusAction(HttpServletRequest request, UserStatusBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult getResult = userProcessor.processUserStatus(getLoginInfoBean().getUserId(), bean.getType(), bean.getStatus());
        if (!getResult.isStatus()) {
            return new JsonDataResult<>(getResult.getMessage());
        }
        return JsonDataResult.success((Map) getResult.getResult());
    }
}
