package com.zuosuo.treehole.tool;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.Json;
import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.entity.BiuWechatFilterTraceEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.tool.HttpTool;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.config.SystemOption;
import com.zuosuo.treehole.processor.WechatProcessor;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.task.ProcessWechatFilterTask;
import com.zuosuo.wechat.WechatMiniTool;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Component("WechatTool")
public class WechatTool {

    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private MiniWechatConfig miniWechatConfig;
    @Autowired
    private WechatProcessor wechatProcessor;

    private FuncResult processFilterContentEntity(JSONObject object, long userId, int useType) {
        if (object.containsKey("errcode") && object.getInteger("errcode") == 0 && object.containsKey("trace_id")) {
            String responseResult = object.containsKey("result") ? JsonTool.toJson(object.getJSONObject("result")) : "";
            String responseDetail = object.containsKey("detail") ? JsonTool.toJson(object.getJSONArray("detail")) : "";
            BiuWechatFilterTraceEntity entity = new BiuWechatFilterTraceEntity();
            entity.setFilterType(ProcessWechatFilterTask.FILTER_CONTENT);
            entity.setUseType(useType);
            entity.setUserId(userId);
            entity.setTraceId(object.getString("trace_id"));
            entity.setResult(responseResult);
            entity.setDetail(responseDetail);
            biuDbFactory.getUserDbFactory().getBiuWechatFilterTraceImpl().insert(entity);
            if (responseResult.contains("risky") || responseResult.contains("review")) {
                return new FuncResult(false, "请核对内容");
            }
            if (responseDetail.contains("risky") || responseDetail.contains("review")) {
                return new FuncResult(false, "请核对内容");
            }
        }
        return new FuncResult(true);
    }

    public FuncResult filterContent(long userId, String content, int useType) {
        BiuUserEntity userEntity = userService.find(userId);
        return filterContent(userEntity, content, useType);
    }

    public FuncResult filterContent(BiuUserEntity userEntity, String content, int useType) {
        String token = String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
        FuncResult result = filterContent(token, userEntity.getOpenid(), useType, 2, content, null);
        if (!result.isStatus()) {
            return new FuncResult(false, "过滤请求失败");
        }
        return processFilterContentEntity((JSONObject) result.getResult(), userEntity.getId(), useType);
    }

    public FuncResult filterContent(String token, String openid, int scene, int version, String content, Map<String, Object> params) {
        return filterContent(token, openid, scene, version, content, params, 2);
    }

    public FuncResult filterContent(String token, String openid, int scene, int version, String content, Map<String, Object> params, int times) {
        if (times == 0) {
            return new FuncResult(false);
        }
        times --;
        String url = WechatMiniTool.MESSAGE_FILTER_URL.replace("ACCESS_TOKEN", token);
        Map<String, Object> body = new HashMap<>();
        body.put("openid", openid);
        body.put("scene", scene);
        body.put("version", version);
        body.put("content", content);
        if (params!= null) {
            if (params.containsKey("nickname")) {
                body.put("nickname", params.getOrDefault("nickname", ""));
            }
            if (params.containsKey("title")) {
                body.put("title", params.getOrDefault("title", ""));
            }
            if (params.containsKey("signature")) {
                body.put("signature", params.getOrDefault("signature", ""));
            }
        }
        FuncResult result = HttpTool.postJson(url, body, "utf-8");
        System.out.println(JsonTool.toJson(result) + "times: " + times);
        if (result.isStatus()) {
            JSONObject object = (JSONObject) result.getResult();
            if (object.containsKey("errcode") && object.getInteger("errcode") == 40001) {
                String tokenKey = SystemOption.MINI_ACCESSS_TOKEN_KEY.getValue().replace("#APPID#", miniWechatConfig.appid());
                FuncResult accessResult = wechatProcessor.refreshAccessToken(tokenKey);
                if (accessResult.isStatus()) {
                    filterContent(String.valueOf(accessResult.getResult()), openid, scene, version, content, params, times);
                }
            }
        }
        return result;
    }

    public FuncResult asyncFilterMedia(MultipartFile file) {
        String token = String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
        String url = WechatMiniTool.MEDIA_ASYNC_FILTER_URL.replace("ACCESS_TOKEN", token);
        FuncResult result = HttpTool.uploadJson(url, file);
        System.out.println(JsonTool.toJson(result) + "|| asyncFilterMedia");
        if (!result.isStatus()) {
            return new FuncResult(false, "过滤请求失败");
        }
        JSONObject object = (JSONObject) result.getResult();
        if (object.containsKey("errcode") && object.getInteger("errcode") == 0 && object.containsKey("errmsg") && object.getString("errmsg").equals("ok")) {
            return new FuncResult(true);
        }
        return new FuncResult(false, "请核对内容");
    }

    public void sendMiniTemplateMessage(String token, String openid, String template_id, Map<String, Object> params) {
        sendMiniTemplateMessage(token, openid, template_id, params, 2);
    }

    /**
     * 推送模板消息
     * @param token
     * @param openid
     * @param template_id
     * @param params
     */
    public void sendMiniTemplateMessage(String token, String openid, String template_id, Map<String, Object> params, int times) {
        if (times == 0) {
            return ;
        }
        times --;
        String url = WechatMiniTool.MESSAGE_URL.replace("ACCESS_TOKEN", token);
        Map<String, Object> body = new HashMap<>();
        // 小程序
        body.put("touser", openid);
        body.put("template_id", template_id);
        body.put("page", params.getOrDefault("page", ""));
        body.put("data", params.getOrDefault("data", new HashMap<>()));
        FuncResult result = HttpTool.postJson(url, body, "utf-8");
        System.out.println(JsonTool.toJson(result) + "times: " + times);
        if (result.isStatus()) {
            JSONObject object = (JSONObject) result.getResult();
            if (object.containsKey("errcode") && object.getInteger("errcode") == 40001) {
                String tokenKey = SystemOption.MINI_ACCESSS_TOKEN_KEY.getValue().replace("#APPID#", miniWechatConfig.appid());
                FuncResult accessResult = wechatProcessor.refreshAccessToken(tokenKey);
                if (accessResult.isStatus()) {
                    sendMiniTemplateMessage(String.valueOf(accessResult.getResult()), openid, template_id, params, times);
                }
            }
        }
    }

    public String getAccessToken() {
        return String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
    }
}
