package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserFavorEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserFavorProvider")
public class BiuUserFavorProvider extends AbstractSoftDeleteProvider<BiuUserFavorEntity> {
    public BiuUserFavorProvider() {
        setTable(BiuTableEnum.USER_FAVOR.getValue());
    }
}
