package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuMoodEntity;
import com.zuosuo.biudb.mapper.BiuMoodMapper;
import com.zuosuo.biudb.provider.BiuMoodProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuMoodImpl")
public class BiuMoodImpl extends AbstractImpl<BiuMoodEntity, BiuMoodMapper, BiuMoodProvider> {
}
