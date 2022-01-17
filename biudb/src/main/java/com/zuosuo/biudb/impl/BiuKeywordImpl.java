package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuKeywordEntity;
import com.zuosuo.biudb.mapper.BiuKeywordMapper;
import com.zuosuo.biudb.provider.BiuKeywordProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuKeywordImpl")
public class BiuKeywordImpl extends AbstractImpl<BiuKeywordEntity, BiuKeywordMapper, BiuKeywordProvider> {
}
