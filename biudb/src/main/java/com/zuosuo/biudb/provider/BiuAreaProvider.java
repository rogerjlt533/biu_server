package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuAreaEntity;
import com.zuosuo.mybatis.provider.AbstractProvider;

public class BiuAreaProvider extends AbstractProvider<BiuAreaEntity> {
    public BiuAreaProvider() {
        setTable(BiuTableEnum.AREA.getValue());
    }
}
