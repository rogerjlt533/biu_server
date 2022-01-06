package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuMessageViewEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuMessageViewProvider")
public class BiuMessageViewProvider extends AbstractSoftDeleteProvider<BiuMessageViewEntity> {
    public BiuMessageViewProvider() {
        setTable(BiuTableEnum.MESSAGE_VIEW.getValue());
    }
}
