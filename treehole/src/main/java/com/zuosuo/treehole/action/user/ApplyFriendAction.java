package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.response.ResponseConfig;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.ApplyFriendBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ApplyFriendAction extends BaseAction {

    private ApplyFriendBean bean;
    private UserProcessor userProcessor;

    public ApplyFriendAction(HttpServletRequest request, ApplyFriendBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
//        bean.setFriend(userProcessor.encodeHash(9));
        long friendId = bean.getFriend().isEmpty() ? 0 : userProcessor.decodeHash(bean.getFriend());
        FuncResult processResult = userProcessor.processFriend(getLoginInfoBean().getUserId(), friendId, bean);
        if (!processResult.isStatus()) {
            if (processResult.getResult() != null) {
                Map<String, String> result = (Map<String, String>) processResult.getResult();
                if (result.containsKey("errcode") && result.get("errcode").equals("504")) {
                    return new JsonResult(504, processResult.getMessage());
                } else {
                    return new JsonResult(processResult.getMessage());
                }
            } else {
                return new JsonResult(processResult.getMessage());
            }
        }
        if (bean.getMethod().equals(ApplyFriendBean.COMMUNICATE)) {
            return new JsonDataResult<>(ResponseConfig.SUCCESS_CODE, "", processResult.getResult());
        }
        return JsonResult.success();
    }
}
