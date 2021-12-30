package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserMessageReadBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UserMessageReadAction extends BaseAction {

    private UserMessageReadBean bean;
    private UserProcessor userProcessor;

    public UserMessageReadAction(HttpServletRequest request, UserMessageReadBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        long messageId = userProcessor.decodeHash(bean.getId());
        FuncResult processResult = userProcessor.readMessage(getLoginInfoBean().getUserId(), messageId);
        if (!processResult.isStatus()) {
            return new JsonDataResult<>(processResult.getMessage());
        }
        return JsonDataResult.success((Map) userProcessor.readingMessageCount(getLoginInfoBean().getUserId()));
    }
}
