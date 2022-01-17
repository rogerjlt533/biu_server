package com.zuosuo.biudb.factory;

import com.zuosuo.biudb.impl.BiuAreaImpl;
import com.zuosuo.biudb.impl.BiuKeywordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BiuCommonDbFactory")
public class BiuCommonDbFactory {

    @Autowired
    private BiuAreaImpl biuAreaImpl;
    @Autowired
    private BiuKeywordImpl biuKeywordImpl;

    public BiuAreaImpl getBiuAreaImpl() {
        return biuAreaImpl;
    }

    public BiuKeywordImpl getBiuKeywordImpl() {
        return biuKeywordImpl;
    }
}
