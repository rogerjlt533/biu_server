package com.zuosuo.treehole.action.note;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NoteFavorAction extends BaseAction {

    public NoteFavorAction(HttpServletRequest request) {
        super(request);
    }

    @Override
    public JsonDataResult<Map> run() {
        return null;
    }
}
