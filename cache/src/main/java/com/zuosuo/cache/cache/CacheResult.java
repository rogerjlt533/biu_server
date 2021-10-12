package com.zuosuo.cache.cache;

public class CacheResult<T> {
    private boolean status;
    private T result;

    public CacheResult() {
    }

    public CacheResult(boolean status, T result) {
        this.status = status;
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public CacheResult setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public T getResult() {
        return result;
    }

    public CacheResult<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
