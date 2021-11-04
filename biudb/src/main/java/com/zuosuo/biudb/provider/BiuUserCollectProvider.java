package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserCollectEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserCollectProvider")
public class BiuUserCollectProvider extends AbstractSoftDeleteProvider<BiuUserCollectEntity> {
    public BiuUserCollectProvider() {
        setTable(BiuTableEnum.USER_COLLECT.getValue());
    }
}
