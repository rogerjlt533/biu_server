package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.response.ResponseConfig;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.SignCommunicateBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SignCommunicateAction extends BaseAction {

    private SignCommunicateBean bean;
    private UserProcessor userProcessor;


    public SignCommunicateAction(HttpServletRequest request, SignCommunicateBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
//        bean.setFriend(userProcessor.encodeHash(4));
//        bean.setLog(userProcessor.encodeHash(2));
//        getLoginInfoBean().setUserId(9);
        FuncResult processResult = userProcessor.processSignCommunicate(getLoginInfoBean().getUserId(), bean);
        if (!processResult.isStatus()) {
            return new JsonDataResult<>(processResult.getMessage());
        }
        return JsonDataResult.success((Map) processResult.getResult());
    }
}
