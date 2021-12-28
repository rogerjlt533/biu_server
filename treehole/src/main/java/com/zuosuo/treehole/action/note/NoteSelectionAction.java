package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NoteSelectionAction extends BaseAction {

    private UserProcessor userProcessor;

    public NoteSelectionAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public JsonDataResult<Map> run() {
        FuncResult processResult = userProcessor.getNoteInitSelection(getLoginInfoBean().getUserId());
//        if (!processResult.isStatus()) {
//            return new JsonDataResult<>(processResult.getMessage());
//        }
        return JsonDataResult.success((Map) processResult.getResult());
    }
}
