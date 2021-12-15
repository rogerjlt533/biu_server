package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.NoteCommentBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NoteCommentAction extends BaseAction {

    private NoteCommentBean bean;
    private UserProcessor userProcessor;

    public NoteCommentAction(HttpServletRequest request, NoteCommentBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult processResult = userProcessor.processNoteComment(getLoginInfoBean().getUserId(), bean);
        if (!processResult.isStatus()) {
            return new JsonDataResult<>(processResult.getMessage());
        }
        return JsonDataResult.success((Map) processResult.getResult());
    }
}
