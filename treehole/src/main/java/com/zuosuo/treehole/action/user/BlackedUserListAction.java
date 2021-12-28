package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.result.BlackUserResult;
import com.zuosuo.treehole.result.CollectUserResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BlackedUserListAction extends BaseAction {

    private UserProcessor userProcessor;

    public BlackedUserListAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<List<BlackUserResult>> run() {
        FuncResult getResult = userProcessor.getBlackList(getLoginInfoBean().getUserId());
//        if (!getResult.isStatus()) {
//            return new JsonDataResult<>(getResult.getMessage());
//        }
        return JsonDataResult.success((List<BlackUserResult>) getResult.getResult());
    }
}
