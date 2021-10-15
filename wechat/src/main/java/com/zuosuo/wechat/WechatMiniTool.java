package com.zuosuo.wechat;

import com.alibaba.fastjson.JSONObject;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.tool.HttpTool;
import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class WechatMiniTool {

    public static final String SESSION_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    public static SessionInfo code2Session(String code, WechatConfig config) {
        String url = SESSION_CODE_URL.replace("APPID", config.appid()).replace("SECRET", config.appsecret()).replace("JSCODE", code);
        FuncResult result = HttpTool.get(url);
        if (!result.isStatus()) {
            return null;
        }
        HttpEntity entity = (HttpEntity) result.getResult();
        String content = null;
        try {
            content = new BufferedReader(new InputStreamReader(entity.getContent())).lines().parallel().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return null;
        }
        JSONObject obj = JSONObject.parseObject(content);
        return new SessionInfo(obj.getInteger("errcode"), obj.getString("errmsg"), obj.getString("openid"), obj.getString("session_key"), obj.getString("unionid"));
    }
}
