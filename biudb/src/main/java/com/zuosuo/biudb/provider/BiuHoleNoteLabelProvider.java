package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuHoleNoteLabelEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuHoleNoteLabelProvider")
public class BiuHoleNoteLabelProvider extends AbstractSoftDeleteProvider<BiuHoleNoteLabelEntity> {
    public BiuHoleNoteLabelProvider() {
        setTable(BiuTableEnum.HOLE_NOTE_LABEL.getValue());
    }
}
