package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
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
        if (!bean.getContent().isEmpty() && !userProcessor.getKeywordService().verifyKeyword(bean.getContent())) {
            return new JsonDataResult<>("请查看是否有敏感词");
        }
        FuncResult processResult = userProcessor.processNoteComment(getLoginInfoBean().getUserId(), bean);
        if (!processResult.isStatus()) {
            if (processResult.getMessage() != null && processResult.getMessage().equals("输入信息违规")) {
                return new JsonDataResult<>(503, processResult.getMessage());
            } else {
                return new JsonDataResult<>(processResult.getMessage());
            }
        }
        return JsonDataResult.success((Map) processResult.getResult());
    }
}
