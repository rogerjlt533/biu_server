package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
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
        FuncResult getResult = userProcessor.getIndexList(getLoginInfoBean().getUserId(), bean);
//        if (!getResult.isStatus()) {
//            return new JsonDataResult<>(getResult.getMessage());
//        }
        return JsonDataResult.success((Map) getResult.getResult());
    }
}
