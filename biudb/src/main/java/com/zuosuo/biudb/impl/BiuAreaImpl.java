package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuAreaEntity;
import com.zuosuo.biudb.mapper.BiuAreaMapper;
import com.zuosuo.biudb.provider.BiuAreaProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuAreaImpl")
public class BiuAreaImpl extends AbstractImpl<BiuAreaEntity, BiuAreaMapper, BiuAreaProvider> {
}
