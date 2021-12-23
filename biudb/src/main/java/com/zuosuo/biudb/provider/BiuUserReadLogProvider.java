package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserReadLogEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserReadLogProvider")
public class BiuUserReadLogProvider extends AbstractSoftDeleteProvider<BiuUserReadLogEntity> {
    public BiuUserReadLogProvider() {
        setTable(BiuTableEnum.USER_READ_LOG.getValue());
    }
}
