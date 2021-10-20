package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserProvider")
public class BiuUserProvider extends AbstractSoftDeleteProvider<BiuUserEntity> {
    public BiuUserProvider() {
        setTable(BiuTableEnum.USER.getValue());
    }
}
