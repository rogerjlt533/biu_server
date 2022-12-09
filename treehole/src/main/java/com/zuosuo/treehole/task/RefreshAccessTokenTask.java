package com.zuosuo.treehole.task;

import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.config.SystemOption;
import com.zuosuo.treehole.processor.WechatProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("RefreshAccessTokenTask")
public class RefreshAccessTokenTask {

    @Autowired
    private MiniWechatConfig miniWechatConfig;
    @Autowired
    private WechatProcessor wechatProcessor;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void execute() {
        String tokenKey = SystemOption.MINI_ACCESSS_TOKEN_KEY.getValue().replace("#APPID#", miniWechatConfig.appid());
        wechatProcessor.refreshAccessToken(tokenKey);
    }
}
