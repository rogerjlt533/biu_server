package com.zuosuo.treehole.action.login;

import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.bean.LoginCodeBean;
import com.zuosuo.treehole.processor.WechatProcessor;
import com.zuosuo.wechat.SessionInfo;
import com.zuosuo.wechat.WechatConfig;
import com.zuosuo.wechat.WechatMiniTool;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 微信code登录
 */
public class LoginCodeAction extends BaseAction {

    private LoginCodeBean bean;
    private WechatProcessor wechatProcessor;
    private WechatConfig miniWechatConfig;

    public LoginCodeAction(HttpServletRequest request, LoginCodeBean bean, WechatProcessor wechatProcessor) {
        super(request);
        this.bean = bean;
        this.wechatProcessor = wechatProcessor;
        this.miniWechatConfig = wechatProcessor.getMiniWechatConfig();
    }

    @Override
    public JsonDataResult<Map> run() {
        SessionInfo session = WechatMiniTool.code2Session(bean.getCode(), miniWechatConfig);
        if (session == null) {
            return new JsonDataResult<>(501, "授权失败");
        }
        if (session.getOpenid() == null) {
            return new JsonDataResult<>("授权失败!");
        }
        FuncResult loginResult = wechatProcessor.loginCode(request, session);
        if (!loginResult.isStatus()) {
            return new JsonDataResult<>(501, "登录失败");
        }
        Map<String, String> data = (Map<String, String>) loginResult.getResult();
        return JsonDataResult.success(data);
    }
}
