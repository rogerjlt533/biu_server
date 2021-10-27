package com.zuosuo.component.response;

public class JsonResult {
    private int code;
    private String message;

    public JsonResult() {
        code = ResponseConfig.ERROR_CODE;
        message = "";
    }

    public JsonResult(int code) {
        this.code = code;
        message = "";
    }

    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static JsonResult success() {
        return new JsonResult(ResponseConfig.SUCCESS_CODE);
    }
}
