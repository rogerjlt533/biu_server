package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuMessageEntity;
import com.zuosuo.biudb.mapper.BiuMessageMapper;
import com.zuosuo.biudb.provider.BiuMessageProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuMessageImpl")
public class BiuMessageImpl extends AbstractImpl<BiuMessageEntity, BiuMessageMapper, BiuMessageProvider> {
}
