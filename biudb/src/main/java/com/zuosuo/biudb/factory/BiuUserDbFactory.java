package com.zuosuo.biudb.factory;

import com.zuosuo.biudb.impl.BiuInterestImpl;
import com.zuosuo.biudb.impl.BiuMessageImpl;
import com.zuosuo.biudb.impl.BiuUserImpl;
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

    public BiuUserImpl getBiuUserImpl() {
        return biuUserImpl;
    }

    public BiuInterestImpl getBiuInterestImpl() {
        return biuInterestImpl;
    }

    public BiuMessageImpl getBiuMessageImpl() {
        return biuMessageImpl;
    }
}
