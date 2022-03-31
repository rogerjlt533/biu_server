package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserIndexViewEntity;
import com.zuosuo.biudb.mapper.BiuUserIndexViewMapper;
import com.zuosuo.biudb.provider.BiuUserIndexViewProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserIndexViewImpl")
public class BiuUserIndexViewImpl extends AbstractImpl<BiuUserIndexViewEntity, BiuUserIndexViewMapper, BiuUserIndexViewProvider> {
}
