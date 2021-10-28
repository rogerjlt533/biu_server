package com.zuosuo.component.response;

public class JsonDataResult<T> extends JsonResult {

    public JsonDataResult() {
        super();
    }

    public JsonDataResult(int code) {
        super(code);
    }

    public JsonDataResult(String message) {
        super(message);
    }

    public JsonDataResult(int code, String message) {
        super(code, message);
    }

    public JsonDataResult(int code, String message, T data) {
        super(code, message);
        setData(data);
    }

    protected T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> JsonDataResult<T> success(T data) {
        return new JsonDataResult<>(ResponseConfig.SUCCESS_CODE, "", data);
    }

    public static <T> JsonDataResult<T> success(String message, T data) {
        return new JsonDataResult<>(ResponseConfig.SUCCESS_CODE, message, data);
    }
}
