package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuHoleNoteListViewEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuHoleNoteListViewProvider")
public class BiuHoleNoteListViewProvider extends AbstractSoftDeleteProvider<BiuHoleNoteListViewEntity> {
    public BiuHoleNoteListViewProvider() {
        setTable(BiuTableEnum.HOLE_NOTE_LIST_VIEW.getValue());
    }
}
