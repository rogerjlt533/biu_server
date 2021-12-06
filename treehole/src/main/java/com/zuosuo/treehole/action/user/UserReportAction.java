package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.UserReportBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户建议举报
 */
public class UserReportAction extends BaseAction {

    private UserReportBean bean;
    private UserProcessor userProcessor;

    public UserReportAction(HttpServletRequest request, UserReportBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        long relateId = bean.getRelate().isEmpty() ? 0 : userProcessor.decodeHash(bean.getRelate());
        FuncResult loginResult = userProcessor.submitReport(bean.getType(), getLoginInfoBean().getUserId(), relateId, bean.getContent(), bean.getImages());
        if (!loginResult.isStatus()) {
            return new JsonResult(loginResult.getMessage());
        }
        return JsonResult.success();
    }
}
