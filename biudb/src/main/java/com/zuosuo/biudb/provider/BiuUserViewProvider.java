package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserViewEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserViewProvider")
public class BiuUserViewProvider extends AbstractSoftDeleteProvider<BiuUserViewEntity> {
    public BiuUserViewProvider() {
        setTable(BiuTableEnum.USER_VIEW.getValue());
    }
}
