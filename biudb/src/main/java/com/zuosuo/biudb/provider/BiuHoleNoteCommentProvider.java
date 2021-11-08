package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuHoleNoteCommentEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuHoleNoteCommentProvider")
public class BiuHoleNoteCommentProvider extends AbstractSoftDeleteProvider<BiuHoleNoteCommentEntity> {
    public BiuHoleNoteCommentProvider() {
        setTable(BiuTableEnum.HOLE_NOTE_COMMENT.getValue());
    }
}
