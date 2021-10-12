package com.zuosuo.cache.cache;


import com.zuosuo.cache.redis.AbstractRedisTool;
import com.zuosuo.component.response.FuncResult;

public abstract class BaseCache {

    public abstract String prefix();
    public abstract String module();
    public abstract AbstractRedisTool getRedisTool();

    public String key(String method, String[] args) {
        String key = prefix() + ":" + module() + ":" + method;
        if (args.length == 0) {
            return key;
        }
        for (String item: args) {
            key += ":" + item;
        }
        return key;
    }

    public String emptyCountKey(String key) {
        return key + ":empty:count";
    }

    public <T, R extends CacheResult<T>> R getValue(String key, Class<T> clazz, long ttl) {
        FuncResult result = getRedisTool().getValueOperator().get(key, clazz);
        if (result.isStatus()) {
            return (R) new CacheResult<>().setStatus(true).setResult(result.getResult());
        }
        String emptyKey = emptyCountKey(key);
        FuncResult emptyResult = getRedisTool().getValueOperator().get(emptyKey, Integer.class);
        if (emptyResult.isStatus() && (int) emptyResult.getResult() > 0) {
            return (R) new CacheResult<>().setStatus(true);
        }
        setEmptyCount(emptyKey, ttl == 0 ? 60: ttl);
        return (R) new CacheResult<>();
    }

    public <T, R extends CacheResult<T>> R getValue(String key, Class<T> clazz) {
        return getValue(key, clazz, 0);
    }

    public CacheResult<String> getValue(String key, long ttl) {
        return getValue(key, String.class, ttl);
    }

    public CacheResult<String> getValue(String key) {
        return getValue(key, String.class);
    }

    public void setValue(String key, Object value) {
        getRedisTool().getValueOperator().set(key, value);
    }

    public void setValue(String key, Object value, long ttl) {
        getRedisTool().getValueOperator().set(key, value, ttl);
    }

    public void setEmptyCount(String key) {
        setEmptyCount(key, 0);
    }

    public void setEmptyCount(String key, long ttl) {
        getRedisTool().getValueOperator().increment(key, 1, ttl);
    }
}
