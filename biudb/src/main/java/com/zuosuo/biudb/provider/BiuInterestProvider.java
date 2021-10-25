package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuInterestEntity;
import com.zuosuo.mybatis.provider.AbstractProvider;
import org.springframework.stereotype.Component;

@Component("BiuInterestProvider")
public class BiuInterestProvider extends AbstractProvider<BiuInterestEntity> {
    public BiuInterestProvider() {
        setTable(BiuTableEnum.INTEREST.getValue());
    }
}
