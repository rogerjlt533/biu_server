package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuMoodEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuMoodProvider")
public class BiuMoodProvider extends AbstractSoftDeleteProvider<BiuMoodEntity> {
    public BiuMoodProvider() {
        setTable(BiuTableEnum.MOOD.getValue());
    }
}
