package com.zuosuo.cache.redis;

import org.springframework.data.redis.core.HashOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HashOperator {

    private HashOperations<String, Object, Object> operations;
    private AbstractRedisTool redisTool;

    public HashOperator(HashOperations<String, Object, Object> operations, AbstractRedisTool redisTool) {
        this.operations = operations;
        this.redisTool = redisTool;
    }

    public HashOperations<String, Object, Object> getOperations() {
        return operations;
    }

    public void put(String name, String key, Object value) {
        operations.put(name, key, value);
    }

    public void put(String name, Map<Object, Object> data) {
        operations.putAll(name, data);
    }

    public void put(String name, Map<Object, Object> data, long timeout, TimeUnit timeType) {
        operations.putAll(name, data);
        if (timeout > 0) {
            redisTool.expire(name, timeout, timeType);
        }
    }

    public Object get(String name, Object key) {
        return operations.get(name, key);
    }

    public Map<Object, Object> multiGet(String name) {
        return operations.entries(name);
    }

    public Map<Object, Object> multiGet(String name, List keys) {
        Map<Object, Object> record = multiGet(name);
        Map<Object, Object> result = new HashMap<>();
        for (Object key: keys) {
            result.put(key, record.get(key));
        }
        return result;
    }

    public long delete(String name, Object key) {
        return operations.delete(name, key);
    }

    public void delete(String name, List keys) {
        for (Object key: keys) {
            operations.delete(name, key);
        }
    }

    public void increment(String name, Object key) {
        operations.increment(name, key, 1L);
    }

    public void increment(String name, Object key, long disc) {
        operations.increment(name, key, disc);
    }

    public void increment(String name, Object key, double disc) {
        operations.increment(name, key, disc);
    }
}
