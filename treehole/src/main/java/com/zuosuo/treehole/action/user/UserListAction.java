package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserListBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UserListAction extends BaseAction {

    private UserListBean bean;
    private UserProcessor userProcessor;

    public UserListAction(HttpServletRequest request, UserListBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        return new JsonDataResult<>();
    }
}
