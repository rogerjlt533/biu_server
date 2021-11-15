package com.zuosuo.qiniu.tool;

public class QiniuResult {
    private boolean status;
    private String message, url, hash;

    public QiniuResult() {
        status = false;
        message = "";
        url = "";
        hash = "";
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
