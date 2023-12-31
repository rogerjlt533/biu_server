package com.zuosuo.biudb.redis;

import com.zuosuo.cache.redis.BaseRedisProperity;

public enum BiuRedisProperty implements BaseRedisProperity {
    // 2-测试 3-新的正式 1-老的正式
    BIU("localhost", 6379, "", 3);

    private String host;
    private int port;
    private String password;
    private int index;

    private BiuRedisProperty(String host, int port, String password, int index) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.index = index;
    }

    @Override
    public String host() {
        return host;
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public int index() {
        return index;
    }
}
