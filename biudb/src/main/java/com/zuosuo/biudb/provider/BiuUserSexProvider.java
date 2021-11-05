package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserSexEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserSexProvider")
public class BiuUserSexProvider extends AbstractSoftDeleteProvider<BiuUserSexEntity> {
    public BiuUserSexProvider() {
        setTable(BiuTableEnum.USER_SEX.getValue());
    }
}
