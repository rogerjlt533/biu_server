package com.zuosuo.cache.redis;

import com.zuosuo.component.phprpc.SerializerTool;
import com.zuosuo.component.response.FuncResult;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class ValueOperator {

    private ValueOperations<String, String> operations;
    private AbstractRedisTool redisTool;

    public ValueOperator(ValueOperations<String, String> operations, AbstractRedisTool redisTool) {
        this.operations = operations;
        this.redisTool = redisTool;
    }

    public ValueOperations<String, String> getOperations() {
        return operations;
    }

    public void set(String name, Object value) {
        set(name, value, 0);
    }

    public void set(String name, Object value, long timeout) {
        set(name, value, timeout, TimeUnit.SECONDS);
    }

    public void set(String name, Object value, long timeout, TimeUnit timeType) {
        String result = SerializerTool.serializer(value);
        operations.set(name, result);
        if (timeout > 0) {
            redisTool.expire(name, timeout, timeType);
        }
    }

    public void increment(String name) {
        increment(name, 1, 0);
    }

    public void increment(String name, long disc, long timeout) {
        increment(name, disc, timeout, TimeUnit.SECONDS);
    }

    public void increment(String name, long disc, long timeout, TimeUnit timeType) {
        FuncResult result = get(name, Long.class);
        if (result.isStatus()) {
            set(name, (Long) result.getResult() + disc);
        } else {
            set(name, disc);
        }
        if (timeout > 0) {
            redisTool.expire(name, timeout, timeType);
        }
    }

    public void increment(String name, double disc, long timeout) {
        increment(name, disc, timeout, TimeUnit.SECONDS);
    }

    public void increment(String name, double disc, long timeout, TimeUnit timeType) {
        FuncResult result = get(name, Double.class);
        if (result.isStatus()) {
            set(name, (Double) result.getResult() + disc);
        } else {
            set(name, disc);
        }
        if (timeout > 0) {
            redisTool.expire(name, timeout, timeType);
        }
    }

    public boolean setnx(String name, Object value) {
        return setnx(name, value, 0);
    }

    public boolean setnx(String name, Object value, long timeout) {
        return setnx(name, value, timeout, TimeUnit.SECONDS);
    }

    public boolean setnx(String name, Object value, long timeout, TimeUnit timeType) {
        boolean result = operations.setIfAbsent(name, SerializerTool.serializer(value));
        if (timeout > 0) {
            redisTool.expire(name, timeout, timeType);
        }
        return result;
    }

    public <T> FuncResult get(String name, Class<T> clazz) {
        String value = operations.get(name);
        if (value != null && !value.isEmpty()) {
            T result = SerializerTool.unserialize(value, clazz);
            if (result != null) {
                return new FuncResult(true, "", result);
            }
        }
        return new FuncResult();
    }
}
