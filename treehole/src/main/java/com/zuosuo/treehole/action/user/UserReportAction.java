package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.tool.JsonTool;
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
        if (!bean.getContent().isEmpty() && !userProcessor.getKeywordService().verifyKeyword(bean.getContent())) {
            return new JsonResult("请查看是否有敏感词");
        }
        long relateId = bean.getRelate().isEmpty() ? 0 : userProcessor.decodeHash(bean.getRelate());
        FuncResult loginResult = userProcessor.submitReport(bean.getType(), getLoginInfoBean().getUserId(), relateId, bean.getContent(), bean.getImages());
        if (!loginResult.isStatus()) {
            System.out.println(JsonTool.toJson(new JsonResult(loginResult.getMessage())));
            if (loginResult.getMessage() != null && loginResult.getMessage().equals("输入信息违规")) {
                return new JsonResult(503, loginResult.getMessage());
            } else {
                return new JsonResult(loginResult.getMessage());
            }
        }
        return JsonResult.success();
    }
}
