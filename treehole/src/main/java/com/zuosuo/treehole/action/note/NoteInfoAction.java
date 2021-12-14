package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.NoteInfoBean;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NoteInfoAction extends BaseAction {

    private NoteInfoBean bean;
    private UserProcessor userProcessor;

    public NoteInfoAction(HttpServletRequest request, NoteInfoBean bean, UserProcessor userProcessor) {
        super(request);
        this.bean = bean;
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult processResult = userProcessor.noteInfo(getLoginInfoBean().getUserId(), bean);
        if (!processResult.isStatus()) {
            return new JsonDataResult<>(processResult.getMessage());
        }
        return JsonDataResult.success((Map) processResult.getResult());
    }
}
