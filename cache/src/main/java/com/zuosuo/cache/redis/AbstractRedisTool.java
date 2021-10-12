package com.zuosuo.cache.redis;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRedisTool {

    public abstract StringRedisTemplate getTemplate();
    public abstract int getDatabase();

    public JedisConnectionFactory getFactory() {
        return (JedisConnectionFactory) getTemplate().getConnectionFactory();
    }

    public AbstractRedisTool select(int dbIndex) {
        getFactory().setDatabase(dbIndex);
        return this;
    }

    public AbstractRedisTool init() {
        return select(getDatabase());
    }

    public boolean exists(String name) {
        return getTemplate().hasKey(name);
    }

    public long delete(List<String> keys) {
        return getTemplate().delete(keys);
    }

    public boolean expire(String name, long timeout) {
        return getTemplate().expire(name, timeout, TimeUnit.SECONDS);
    }

    public boolean expire(String name, long timeout, TimeUnit timeType) {
        return getTemplate().expire(name, timeout, timeType);
    }

    public ValueOperator getValueOperator() {
        return new ValueOperator(init().getTemplate().opsForValue(), this);
    }

    public ValueOperator getValueOperator(int db) {
        AbstractRedisTool tool = this.select(db);
        return new ValueOperator(tool.getTemplate().opsForValue(), tool);
    }

    public HashOperator getHashOperator() {
        return new HashOperator(init().getTemplate().opsForHash(), this);
    }

    public HashOperator getHashOperator(int db) {
        AbstractRedisTool tool = this.select(db);
        return new HashOperator(tool.getTemplate().opsForHash(), tool);
    }
}




