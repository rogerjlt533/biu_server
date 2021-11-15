package com.zuosuo.qiniu.tool;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zuosuo.qiniu.config.BaseQiniuConfig;

public abstract class AbstractQiniuTool {

    public abstract BaseQiniuConfig getConfig();
    public abstract Configuration getStorageConfig();

    public Configuration getStorageConfig(Region region) {
        return new Configuration(region);
    }

    public UploadManager getUploadManager() {
        return new UploadManager(getStorageConfig());
    }

    public Auth getAuth() {
        return Auth.create(getConfig().getAccessKey(), getConfig().getSecretKey());
    }

    public String getUploadToken() {
        return getAuth().uploadToken(getConfig().getBucket());
    }
}
