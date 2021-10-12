package com.zuosuo.component.response;

import java.util.function.Function;

public class FuncResult {

    private boolean status;
    private String message;
    private Object result;

    public FuncResult() {
        status = false;
        message = "";
    }

    public FuncResult(boolean status) {
        this.status = status;
    }

    public FuncResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public FuncResult(boolean status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
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

    public Object getResult() {
        return result;
    }

    public <T, R> R getResult(Function<T, R> func) {
        return func.apply((T) result);
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
