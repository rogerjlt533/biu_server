package com.zuosuo.treehole.bean;

public class VerifyResult {
    private boolean status;
    private String message;

    public VerifyResult() {
        status = true;
        message = "";
    }

    public VerifyResult(String message) {
        status = false;
        this.message = message;
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
}
