package com.zuosuo.treehole.action.login;

import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.ResponseConfig;
import com.zuosuo.treehole.bean.LoginCodeBean;
import com.zuosuo.treehole.processor.WechatUserLoginProcessor;
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
    private WechatConfig wechatConfig;
    private WechatUserLoginProcessor loginProcessor;
    private BiuDbFactory dbFactory;

    public LoginCodeAction(HttpServletRequest request, LoginCodeBean bean,WechatConfig wechatConfig, WechatUserLoginProcessor loginProcessor, BiuDbFactory dbFactory) {
        super(request);
        this.bean = bean;
        this.wechatConfig = wechatConfig;
        this.loginProcessor = loginProcessor;
        this.dbFactory = dbFactory;
    }

    @Override
    public JsonDataResult<Map> run() {
        SessionInfo session = WechatMiniTool.code2Session(bean.getCode(), wechatConfig);
        if (session == null) {
            return new JsonDataResult<>(ResponseConfig.ERROR_CODE, "授权失败");
        }
        FuncResult loginResult = loginProcessor.run(request, session, dbFactory);
        if (!loginResult.isStatus()) {
            return new JsonDataResult<>(ResponseConfig.ERROR_CODE, "登录失败");
        }
        Map<String, String> data = (Map<String, String>) loginResult.getResult();
        return new JsonDataResult<Map>(ResponseConfig.SUCCESS_CODE, "", data);
    }
}
