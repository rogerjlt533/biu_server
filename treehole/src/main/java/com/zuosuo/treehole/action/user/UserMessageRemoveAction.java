package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserMessageRemoveBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UserMessageRemoveAction extends BaseAction {

    private UserMessageRemoveBean bean;
    private UserProcessor userProcessor;

    public UserMessageRemoveAction(HttpServletRequest request, UserMessageRemoveBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        long messageId = userProcessor.decodeHash(bean.getId());
        FuncResult processResult = userProcessor.removeMessage(getLoginInfoBean().getUserId(), messageId);
        if (!processResult.isStatus()) {
            return new JsonDataResult<>(processResult.getMessage());
        }
        return JsonDataResult.success((Map) userProcessor.readingMessageCount(getLoginInfoBean().getUserId()).getResult());
    }
}
