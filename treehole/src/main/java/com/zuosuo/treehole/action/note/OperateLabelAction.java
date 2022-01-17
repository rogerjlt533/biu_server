package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.OperateLabelBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class OperateLabelAction extends BaseAction {

    private OperateLabelBean bean;
    private UserProcessor userProcessor;

    public OperateLabelAction(HttpServletRequest request, OperateLabelBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        if (!bean.getTag().isEmpty() && !userProcessor.getKeywordService().verifyKeyword(bean.getTag())) {
            return new JsonDataResult<>("请查看是否有关键词");
        }
        FuncResult processResult = userProcessor.processLabel(getLoginInfoBean().getUserId(), bean);
        if (!processResult.isStatus()) {
            return new JsonDataResult<>(processResult.getMessage());
        }
        return JsonDataResult.success((Map) processResult.getResult());
    }
}
