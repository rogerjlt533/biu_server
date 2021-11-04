package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserCollectEntity;
import com.zuosuo.biudb.mapper.BiuUserCollectMapper;
import com.zuosuo.biudb.provider.BiuUserCollectProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserCollectImpl")
public class BiuUserCollectImpl extends AbstractImpl<BiuUserCollectEntity, BiuUserCollectMapper, BiuUserCollectProvider> {
}
