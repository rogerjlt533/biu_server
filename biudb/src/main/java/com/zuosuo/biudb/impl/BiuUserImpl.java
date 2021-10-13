package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.mapper.BiuUserMapper;
import com.zuosuo.biudb.provider.BiuUserProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserImpl")
public class BiuUserImpl extends AbstractImpl<BiuUserEntity, BiuUserMapper, BiuUserProvider> {
}
