package com.zuosuo.biudb.redis;

import com.zuosuo.cache.redis.BaseRedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class BiuRedisConfig extends BaseRedisConfig {

    private static final int MAX_IDLE = 200; //最大空闲连接数
    private static final int MAX_TOTAL = 1024; //最大连接数
    private static final long MAX_WAIT_MILLIS = 10000; //建立连接最长等待时间

    //------------------------------------
    @Bean(name = "redisBiu")
    public StringRedisTemplate redisCenterTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory(BiuRedisProperty.BIU.host(), BiuRedisProperty.BIU.port(),
                BiuRedisProperty.BIU.password(), MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, BiuRedisProperty.BIU.index()));
        return template;
    }
}
