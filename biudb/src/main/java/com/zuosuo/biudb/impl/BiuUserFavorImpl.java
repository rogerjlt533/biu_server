package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserFavorEntity;
import com.zuosuo.biudb.mapper.BiuUserFavorMapper;
import com.zuosuo.biudb.provider.BiuUserFavorProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserFavorImpl")
public class BiuUserFavorImpl extends AbstractImpl<BiuUserFavorEntity, BiuUserFavorMapper, BiuUserFavorProvider> {
}
