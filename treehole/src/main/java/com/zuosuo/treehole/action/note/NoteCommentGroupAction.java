package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.NoteCommentGroupBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NoteCommentGroupAction extends BaseAction {

    private NoteCommentGroupBean bean;
    private UserProcessor userProcessor;

    public NoteCommentGroupAction(HttpServletRequest request, NoteCommentGroupBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult getResult = userProcessor.getCommentGroupList(getLoginInfoBean().getUserId(), bean);
        return JsonDataResult.success((Map) getResult.getResult());
    }
}
