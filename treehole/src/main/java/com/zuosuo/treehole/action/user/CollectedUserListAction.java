package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.result.CollectUserResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CollectedUserListAction extends BaseAction {

    private UserProcessor userProcessor;

    public CollectedUserListAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<List<CollectUserResult>> run() {
        FuncResult getResult = userProcessor.getCollectList(getLoginInfoBean().getUserId());
        if (!getResult.isStatus()) {
            return new JsonDataResult<>(getResult.getMessage());
        }
        return JsonDataResult.success((List<CollectUserResult>) getResult.getResult());
    }
}
