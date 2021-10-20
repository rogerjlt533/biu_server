package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuAreaEntity;
import com.zuosuo.mybatis.provider.AbstractProvider;
import org.springframework.stereotype.Component;

@Component("BiuAreaProvider")
public class BiuAreaProvider extends AbstractProvider<BiuAreaEntity> {
    public BiuAreaProvider() {
        setTable(BiuTableEnum.AREA.getValue());
    }
}
