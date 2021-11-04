package com.zuosuo.biudb.factory;

import com.zuosuo.biudb.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BiuUserDbFactory")
public class BiuUserDbFactory {

    @Autowired
    private BiuUserImpl biuUserImpl;
    @Autowired
    private BiuInterestImpl biuInterestImpl;
    @Autowired
    private BiuMessageImpl biuMessageImpl;
    @Autowired
    private BiuUserInterestImpl biuUserInterestImpl;
    @Autowired
    private BiuUserCommunicateImpl biuUserCommunicateImpl;
    @Autowired
    private BiuUserImageImpl biuUserImageImpl;
    @Autowired
    private BiuUserCollectImpl biuUserCollectImpl;

    public BiuUserImpl getBiuUserImpl() {
        return biuUserImpl;
    }

    public BiuInterestImpl getBiuInterestImpl() {
        return biuInterestImpl;
    }

    public BiuMessageImpl getBiuMessageImpl() {
        return biuMessageImpl;
    }

    public BiuUserInterestImpl getBiuUserInterestImpl() {
        return biuUserInterestImpl;
    }

    public BiuUserCommunicateImpl getBiuUserCommunicateImpl() {
        return biuUserCommunicateImpl;
    }

    public BiuUserImageImpl getBiuUserImageImpl() {
        return biuUserImageImpl;
    }

    public BiuUserCollectImpl getBiuUserCollectImpl() {
        return biuUserCollectImpl;
    }
}
