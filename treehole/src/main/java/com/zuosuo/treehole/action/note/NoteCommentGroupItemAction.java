package com.zuosuo.treehole.action.note;

import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;

import javax.servlet.http.HttpServletRequest;

public class NoteCommentGroupItemAction extends BaseAction {

    private UserProcessor userProcessor;

    public NoteCommentGroupItemAction(HttpServletRequest request, UserProcessor userProcessor) {
        super(request);
        this.userProcessor = userProcessor;
    }

    @Override
    public Object run() {
        return null;
    }
}
