package com.zuosuo.treehole.config;

import com.zuosuo.qiniu.config.BaseQiniuConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuConfig extends BaseQiniuConfig {
}
