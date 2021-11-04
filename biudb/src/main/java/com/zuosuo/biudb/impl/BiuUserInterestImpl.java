package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserInterestEntity;
import com.zuosuo.biudb.mapper.BiuUserInterestMapper;
import com.zuosuo.biudb.provider.BiuUserInterestProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserInterestImpl")
public class BiuUserInterestImpl extends AbstractImpl<BiuUserInterestEntity, BiuUserInterestMapper, BiuUserInterestProvider> {
}
