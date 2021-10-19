package com.zuosuo.biudb.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BiuDbFactory")
public class BiuDbFactory {

    @Autowired
    private BiuCommonDbFactory commonDbFactory;

    @Autowired
    private BiuUserDbFactory userDbFactory;

    public BiuCommonDbFactory getCommonDbFactory() {
        return commonDbFactory;
    }

    public BiuUserDbFactory getUserDbFactory() {
        return userDbFactory;
    }
}
