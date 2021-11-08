package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuHoleNoteViewEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuHoleNoteViewProvider")
public class BiuHoleNoteViewProvider extends AbstractSoftDeleteProvider<BiuHoleNoteViewEntity> {
    public BiuHoleNoteViewProvider() {
        setTable(BiuTableEnum.HOLE_NOTE_VIEW.getValue());
    }
}
