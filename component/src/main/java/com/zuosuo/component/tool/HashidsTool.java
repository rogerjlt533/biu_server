package com.zuosuo.component.tool;

import org.hashids.Hashids;

public class HashidsTool {

    private Hashids client;

    public HashidsTool(String salt) {
        client = new Hashids(salt);
    }

    public HashidsTool(String salt, int minHashLength) {
        client = new Hashids(salt, minHashLength);
    }

    public HashidsTool(String salt, int minHashLength, String alphabet) {
        client = new Hashids(salt, minHashLength, alphabet);
    }

    public Hashids getClient() {
        return client;
    }

    public String encode(long... number) {
        return client.encode(number);
    }

    public long[] decode(String code) {
        return client.decode(code);
    }

    public long first(String code) {
        long[] numbers = decode(code);
        return numbers[0];
    }
}
