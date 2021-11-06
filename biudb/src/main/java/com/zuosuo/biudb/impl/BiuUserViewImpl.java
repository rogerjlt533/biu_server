package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserViewEntity;
import com.zuosuo.biudb.mapper.BiuUserViewMapper;
import com.zuosuo.biudb.provider.BiuUserViewProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserViewImpl")
public class BiuUserViewImpl extends AbstractImpl<BiuUserViewEntity, BiuUserViewMapper, BiuUserViewProvider> {
}
