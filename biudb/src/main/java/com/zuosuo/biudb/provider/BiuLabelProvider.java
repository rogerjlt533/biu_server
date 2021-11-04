package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuLabelEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuLabelProvider")
public class BiuLabelProvider extends AbstractSoftDeleteProvider<BiuLabelEntity> {
    public BiuLabelProvider() {
        setTable(BiuTableEnum.LABEL.getValue());
    }
}
