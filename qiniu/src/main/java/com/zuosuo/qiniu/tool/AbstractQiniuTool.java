package com.zuosuo.qiniu.tool;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
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

    public QiniuResult upload(String source, String module, String dest) {
        QiniuResult result = new QiniuResult();
        try {
            Response response = getUploadManager().put(source, String.join("/", module, dest), getUploadToken());
            JSONObject object = JSONObject.parseObject(response.bodyString());
            if (object == null) {
                result.setMessage("七牛上传失败！");
            } else if(object.getString("key") != null) {
                result.setStatus(true);
                result.setUrl(object.getString("key"));
                result.setHash(object.getString("hash"));
            }
        } catch (QiniuException e) {
            result.setMessage("七牛上传失败");
        }
        return result;
    }
}
