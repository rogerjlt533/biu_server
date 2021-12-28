package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuHoleNoteCommentEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuHoleNoteCommentViewProvider")
public class BiuHoleNoteCommentViewProvider extends AbstractSoftDeleteProvider<BiuHoleNoteCommentEntity> {
    public BiuHoleNoteCommentViewProvider() {
        setTable(BiuTableEnum.HOLE_NOTE_COMMENT_VIEW.getValue());
    }
}
