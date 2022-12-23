package com.zuosuo.treehole.action.user;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.tool.WechatTool;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class GetAccessTokenAction extends BaseAction {

    private WechatTool wechatTool;

    public GetAccessTokenAction(HttpServletRequest request, WechatTool wechatTool) {
        super(request);
        this.wechatTool = wechatTool;
    }

    @Override
    public JsonDataResult<Map> run() {
        String token = wechatTool.getAccessToken();
        if (token != null && !token.isEmpty()) {
            Map<String, String> result = new HashMap<>();
            result.put("access_token", token);
            return JsonDataResult.success(result);
        }
        return new JsonDataResult<>("token获取失败");
    }
}
