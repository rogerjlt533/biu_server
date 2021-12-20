package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class CancelUserAction extends BaseAction {

    private UserProcessor userProcessor;

    public CancelUserAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        FuncResult processResult = userProcessor.cancelUser(getLoginInfoBean().getUserId());
        if (!processResult.isStatus()) {
            return new JsonResult(processResult.getMessage());
        }
        return JsonResult.success();
    }
}
