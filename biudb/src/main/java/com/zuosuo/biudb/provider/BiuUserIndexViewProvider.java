package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserIndexViewEntity;
import com.zuosuo.mybatis.provider.AbstractProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserIndexViewProvider")
public class BiuUserIndexViewProvider extends AbstractProvider<BiuUserIndexViewEntity> {
    public BiuUserIndexViewProvider() {
        setTable(BiuTableEnum.USER_INDEX_VIEW.getValue());
    }
}
