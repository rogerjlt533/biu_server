package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuHoleNoteMoodEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuHoleNoteMoodProvider")
public class BiuHoleNoteMoodProvider extends AbstractSoftDeleteProvider<BiuHoleNoteMoodEntity> {
    public BiuHoleNoteMoodProvider() {
        setTable(BiuTableEnum.HOLE_NOTE_MOOD.getValue());
    }
}
