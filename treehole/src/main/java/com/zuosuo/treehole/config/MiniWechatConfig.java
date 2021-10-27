package com.zuosuo.treehole.config;

import com.zuosuo.wechat.WechatConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MiniWechatConfig implements WechatConfig {

    @Value("${wechat.mini.appid}")
    private String appid;

    @Value("${wechat.mini.appsecret}")
    private String appsecret;

    @Value("${wechat.mini.token}")
    private String token;

    @Value("${wechat.mini.aes}")
    private String aes;

    @Override
    public String appid() {
        return appid;
    }

    @Override
    public String appsecret() {
        return appsecret;
    }

    @Override
    public String token() {
        return token;
    }

    @Override
    public String aes() {
        return aes;
    }
}
