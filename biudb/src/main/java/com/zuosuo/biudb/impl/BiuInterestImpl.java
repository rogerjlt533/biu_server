package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuInterestEntity;
import com.zuosuo.biudb.mapper.BiuInterestMapper;
import com.zuosuo.biudb.provider.BiuInterestProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuInterestImpl")
public class BiuInterestImpl extends AbstractImpl<BiuInterestEntity, BiuInterestMapper, BiuInterestProvider> {
}
