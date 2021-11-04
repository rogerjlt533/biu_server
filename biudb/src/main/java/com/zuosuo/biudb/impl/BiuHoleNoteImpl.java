package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuHoleNoteEntity;
import com.zuosuo.biudb.mapper.BiuHoleNoteMapper;
import com.zuosuo.biudb.provider.BiuHoleNoteProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuHoleNoteImpl")
public class BiuHoleNoteImpl extends AbstractImpl<BiuHoleNoteEntity, BiuHoleNoteMapper, BiuHoleNoteProvider> {
}
