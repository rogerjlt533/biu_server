package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuHoleNoteEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuHoleNoteProvider")
public class BiuHoleNoteProvider extends AbstractSoftDeleteProvider<BiuHoleNoteEntity> {
    public BiuHoleNoteProvider() {
        setTable(BiuTableEnum.HOLE_NOTE.getValue());
    }
}
