package com.zuosuo.biudb.redis;

import com.zuosuo.cache.redis.AbstractRedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("BiuRedisTool")
public class BiuRedisTool extends AbstractRedisTool {

    @Autowired
    @Qualifier("redisBiu")
    private StringRedisTemplate template;

    @Override
    public StringRedisTemplate getTemplate() {
        return template;
    }

    @Override
    public int getDatabase() {
        return BiuRedisProperty.BIU.index();
    }
}
