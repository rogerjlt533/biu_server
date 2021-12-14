package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.RemoveNoteBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class RemoveNoteAction extends BaseAction {

    private RemoveNoteBean bean;
    private UserProcessor userProcessor;

    public RemoveNoteAction(HttpServletRequest request, RemoveNoteBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonResult run() {
        FuncResult processResult = userProcessor.removeHoleNote(getLoginInfoBean().getUserId(), bean);
        if (!processResult.isStatus()) {
            return new JsonResult(processResult.getMessage());
        }
        return JsonResult.success();
    }
}
