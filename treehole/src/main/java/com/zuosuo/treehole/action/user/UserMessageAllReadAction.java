package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserMessageAllReadBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UserMessageAllReadAction extends BaseAction {

    private UserMessageAllReadBean bean;
    private UserProcessor userProcessor;

    public UserMessageAllReadAction(HttpServletRequest request, UserMessageAllReadBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult processResult = userProcessor.readAllMessage(getLoginInfoBean().getUserId(), bean.getType(), bean.subList());
        if (!processResult.isStatus()) {
            return new JsonDataResult<>(processResult.getMessage());
        }
        return JsonDataResult.success((Map) userProcessor.readingMessageCount(getLoginInfoBean().getUserId()).getResult());
    }
}
