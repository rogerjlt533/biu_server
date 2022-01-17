package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuKeywordEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuKeywordProvider")
public class BiuKeywordProvider extends AbstractSoftDeleteProvider<BiuKeywordEntity> {
    public BiuKeywordProvider() {
        setTable(BiuTableEnum.KEYWORD.getValue());
    }
}
