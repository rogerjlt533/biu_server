package com.zuosuo.biudb.factory;

import com.zuosuo.biudb.impl.BiuUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BiuUserDbFactory")
public class BiuUserDbFactory {

    @Autowired
    private BiuUserImpl biuUserImpl;

    public BiuUserImpl getBiuUserImpl() {
        return biuUserImpl;
    }
}
