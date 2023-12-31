package com.zuosuo.biudb.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BiuRedisFactory")
public class BiuRedisFactory {

    @Autowired
    private BiuRedisTool biuRedisTool;

    public BiuRedisTool getBiuRedisTool() {
        return biuRedisTool;
    }
}
