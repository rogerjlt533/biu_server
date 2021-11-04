package com.zuosuo.cache.redis;

import org.springframework.data.redis.core.ListOperations;

public class ListOperator {

    private ListOperations<String, String> operations;
    private AbstractRedisTool redisTool;

    public ListOperator(ListOperations<String, String> operations, AbstractRedisTool redisTool) {
        this.operations = operations;
        this.redisTool = redisTool;
    }

    public ListOperations<String, String> getOperations() {
        return operations;
    }

    public long size(String key) {
        return operations.size(key);
    }

    public void leftPush(String key, String... value) {
        operations.leftPushAll(key, value);
    }

    public void rightPush(String key, String... value) {
        operations.rightPushAll(key, value);
    }

    public String leftPop(String key) {
        return operations.leftPop(key);
    }

    public String rightPop(String key) {
        return operations.rightPop(key);
    }
}
