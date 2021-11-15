package com.zuosuo.treehole.action.common;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.tool.QiniuTool;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class UploadTokenAction extends BaseAction {

    private QiniuTool qiniuTool;

    public UploadTokenAction(HttpServletRequest request, QiniuTool qiniuTool) {
        super(request);
        this.qiniuTool = qiniuTool;
    }

    @Override
    public JsonDataResult<Map> run() {
        Map<String, String> result = new HashMap<>();
        result.put("token", qiniuTool.getUploadToken());
        return JsonDataResult.success(result);
    }
}
