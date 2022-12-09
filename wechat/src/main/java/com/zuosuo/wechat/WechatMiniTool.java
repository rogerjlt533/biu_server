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
//    public static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN";
    public static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
    public static final String MESSAGE_FILTER_URL = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=ACCESS_TOKEN";
    public static final String MEDIA_FILTER_URL = "https://api.weixin.qq.com/wxa/media_check_async?access_token=ACCESS_TOKEN";
    public static final String MEDIA_ASYNC_FILTER_URL = "https://api.weixin.qq.com/wxa/img_sec_check?access_token=ACCESS_TOKEN";

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
//        content = "{\"session_key\":\"1122\",\"openid\":\"owHl_5ElyxxP314do0GG0tYWm3Sw\"}";
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

//    /**
//     * 推送模板消息
//     * @param token
//     * @param openid
//     * @param template_id
//     * @param params
//     */
//    public static void sendMiniTemplateMessage(String token, String openid, String template_id, Map<String, Object> params) {
//        String url = MESSAGE_URL.replace("ACCESS_TOKEN", token);
//        Map<String, Object> body = new HashMap<>();
//        // 小程序
//        body.put("touser", openid);
//        body.put("template_id", template_id);
//        body.put("page", params.getOrDefault("page", ""));
//        body.put("data", params.getOrDefault("data", new HashMap<>()));
//        FuncResult result = HttpTool.postJson(url, body, "utf-8");
////        if (result.isStatus()) {
////            JSONObject object = (JSONObject) result.getResult();
////            if (object.containsKey("errcode") && object.getInteger("errcode") == 40001) {
////
////            }
////        }
//        System.out.println(HttpTool.getResponseContent((HttpEntity) result.getResult()));
//    }

    public static FuncResult filterMedia(String token, String openid, String media_url, int media_type, int version, long scene) {
        String url = MEDIA_FILTER_URL.replace("ACCESS_TOKEN", token);
        Map<String, Object> body = new HashMap<>();
        body.put("openid", openid);
        body.put("scene", scene);
        body.put("version", version);
        body.put("media_url", media_url);
        body.put("media_type", media_type);
        FuncResult result = HttpTool.postJson(url, body, "utf-8");
//        if (result.isStatus()) {
//            JSONObject object = (JSONObject) result.getResult();
//            if (object.containsKey("errcode") && object.getInteger("errcode") == 40001) {
//
//            }
//        }
        return result;
    }
}
