package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserInterestEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserInterestProvider")
public class BiuUserInterestProvider extends AbstractSoftDeleteProvider<BiuUserInterestEntity> {
    public BiuUserInterestProvider() {
        setTable(BiuTableEnum.USER_INTEREST.getValue());
    }
}
