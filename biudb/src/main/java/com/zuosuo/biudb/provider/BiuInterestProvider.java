package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuInterestEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuInterestProvider")
public class BiuInterestProvider extends AbstractSoftDeleteProvider<BiuInterestEntity> {
    public BiuInterestProvider() {
        setTable(BiuTableEnum.INTEREST.getValue());
    }
}
