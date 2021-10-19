package com.zuosuo.biudb.factory;

import com.zuosuo.biudb.impl.BiuAreaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BiuCommonDbFactory")
public class BiuCommonDbFactory {

    @Autowired
    private BiuAreaImpl biuAreaImpl;

    public BiuAreaImpl getBiuAreaImpl() {
        return biuAreaImpl;
    }
}
