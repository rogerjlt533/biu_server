package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.result.MyInfoResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 我的信息
 */
public class MyInfoAction extends BaseAction {

    @Autowired
    private UserProcessor userProcessor;

    public MyInfoAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<MyInfoResult> run() {
        FuncResult getResult = userProcessor.getUserInfo(getLoginInfoBean().getUserId());
        if (!getResult.isStatus()) {
            return new JsonDataResult<>(getResult.getMessage());
        }
        return JsonDataResult.success((MyInfoResult) getResult.getResult());
    }
}
