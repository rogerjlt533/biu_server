package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.result.CollectUserResult;
import com.zuosuo.treehole.result.UserFriendResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserFriendListAction extends BaseAction {

    private UserProcessor userProcessor;

    public UserFriendListAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<List<UserFriendResult>> run() {
        FuncResult getResult = userProcessor.getUserFriendList(getLoginInfoBean().getUserId());
//        if (!getResult.isStatus()) {
//            return new JsonDataResult<>(getResult.getMessage());
//        }
        return JsonDataResult.success((List<UserFriendResult>) getResult.getResult());
    }
}
