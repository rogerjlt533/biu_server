package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.mybatis.provider.AbstractProvider;

public class BiuUserProvider extends AbstractProvider<BiuUserEntity> {
    public BiuUserProvider() {
        setTable(BiuTableEnum.USER.getValue());
    }
}
