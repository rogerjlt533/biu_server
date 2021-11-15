package com.zuosuo.treehole.tool;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.zuosuo.qiniu.config.BaseQiniuConfig;
import com.zuosuo.qiniu.tool.AbstractQiniuTool;
import com.zuosuo.treehole.config.QiniuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("QiniuTool")
public class QiniuTool extends AbstractQiniuTool {

    @Autowired
    private QiniuConfig config;

    @Override
    public BaseQiniuConfig getConfig() {
        return config;
    }

    @Override
    public Configuration getStorageConfig() {
        return getStorageConfig(Region.region2());
    }
}
