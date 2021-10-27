package com.zuosuo.treehole.factory;

import com.zuosuo.biudb.redis.BiuRedisFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("RedisFactory")
public class RedisFactory {

    @Resource(type = BiuRedisFactory.class)
    private BiuRedisFactory biuRedisFactory;

    public BiuRedisFactory getBiuRedisFactory() {
        return biuRedisFactory;
    }
}
