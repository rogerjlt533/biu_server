package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.CreateNoteBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class CreateNoteAction extends BaseAction {

    private CreateNoteBean bean;
    private UserProcessor userProcessor;

    public CreateNoteAction(HttpServletRequest request, CreateNoteBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        if (!bean.getMethod().equals(CreateNoteBean.CREATE)) {
            return new JsonResult("请选择正确的操作类型");
        }
        if (!bean.getContent().isEmpty() && !userProcessor.getKeywordService().verifyKeyword(bean.getContent())) {
            return new JsonResult("请查看是否有敏感词");
        }
        FuncResult processResult = userProcessor.createHoleNote(getLoginInfoBean().getUserId(), bean);
        if (!processResult.isStatus()) {
            return new JsonResult(processResult.getMessage());
        }
        return JsonResult.success();
    }
}
