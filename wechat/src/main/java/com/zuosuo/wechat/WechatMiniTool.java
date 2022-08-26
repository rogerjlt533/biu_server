package com.zuosuo.wechat;

import com.alibaba.fastjson.JSONObject;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.tool.HttpTool;
import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WechatMiniTool {

    public static final String SESSION_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
    public static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN";

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
//        content = "{\"session_key\":\"1122\",\"openid\":\"1122\"}";
//        content = "{\"session_key\":\"1122\",\"openid\":\"owHl_5J80GHXpCfVXl0J1M6iauXY\"}";
        JSONObject obj = JSONObject.parseObject(content);
        int errcode = obj.getInteger("errcode") != null ? obj.getInteger("errcode") : 0;
        String errmsg = obj.getString("errmsg") != null ? obj.getString("errmsg") : "";
        String openid = obj.getString("openid") != null ? obj.getString("openid") : "";
        String session_key = obj.getString("session_key") != null ? obj.getString("session_key") : "";
        return new SessionInfo(errcode, errmsg, openid, session_key, "");
    }

    /**
     * 获取access_token
     * @param config
     * @return
     */
    public static AccessTokenInfo getAccessToken(WechatConfig config) {
        String url = TOKEN_URL.replace("APPID", config.appid()).replace("SECRET", config.appsecret());
        FuncResult result = HttpTool.get(url);
        if (!result.isStatus()) {
            return new AccessTokenInfo();
        }
        HttpEntity entity = (HttpEntity) result.getResult();
        String content = null;
        try {
            content = new BufferedReader(new InputStreamReader(entity.getContent())).lines().parallel().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return new AccessTokenInfo();
        }
        JSONObject obj = JSONObject.parseObject(content);
        String access_token = obj.getString("access_token") != null ? obj.getString("access_token") : "";
        int expires_in = obj.getInteger("expires_in") != null ? obj.getInteger("expires_in") : 0;
        return new AccessTokenInfo(access_token, expires_in);
    }

    /**
     * 推送模板消息
     * @param config
     * @param token
     * @param openid
     * @param template
     * @param params
     */
    public static void sendTemplateMessage(WechatConfig config, String token, String openid, TemplateInfo template, Map<String, Object> params) {
        String url = MESSAGE_URL.replace("ACCESS_TOKEN", token);
        int type = template.getType();
        Map<String, Object> body = new HashMap<String, Object>() {{
            put("touser", openid);
        }};
        Map<String, Object> message = new HashMap<>();
        if (type == 1) {
            // 小程序
            message.put("template_id", template.getId());
            message.put("form_id", params.getOrDefault("formid", ""));
            message.put("page", params.getOrDefault("page", ""));
            message.put("data", params.getOrDefault("data", new HashMap<>()));
            body.put("weapp_template_msg", message);
            HttpTool.post(url, body, "utf-8");
        } else if (type == 2) {
            // 公众号
//            message.put("appid", config.appid());
//            message.put("template_id", template.getId());
//            message.put("form_id", template.getForm());
//            message.put("page", params.getOrDefault("page", ""));
//            message.put("data", params.getOrDefault("data", new HashMap<>()));
//            body.put("mp_template_msg", message);
        }
    }
}
