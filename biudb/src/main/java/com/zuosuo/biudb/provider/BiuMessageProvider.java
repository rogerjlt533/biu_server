package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuMessageEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuMessageProvider")
public class BiuMessageProvider extends AbstractSoftDeleteProvider<BiuMessageEntity> {
    public BiuMessageProvider() {
        setTable(BiuTableEnum.MESSAGE.getValue());
    }
}
