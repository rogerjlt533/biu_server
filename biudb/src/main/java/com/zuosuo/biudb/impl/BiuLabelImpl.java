package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuLabelEntity;
import com.zuosuo.biudb.mapper.BiuLabelMapper;
import com.zuosuo.biudb.provider.BiuLabelProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuLabelImpl")
public class BiuLabelImpl extends AbstractImpl<BiuLabelEntity, BiuLabelMapper, BiuLabelProvider> {
}
