package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserFriendCommunicateLogBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class UserFriendCommunicateLogsAction extends BaseAction {

    private UserFriendCommunicateLogBean bean;
    private UserProcessor userProcessor;

    public UserFriendCommunicateLogsAction(HttpServletRequest request, UserFriendCommunicateLogBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<List<Map>> run() {
        long id = bean.getId().isEmpty() ? 0 : userProcessor.decodeHash(bean.getId());
        FuncResult getResult = userProcessor.getUserFriendCommunicateLogList(id, getLoginInfoBean().getUserId());
        return JsonDataResult.success((List<Map>) getResult.getResult());
    }
}
