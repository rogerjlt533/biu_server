package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserSexEntity;
import com.zuosuo.biudb.mapper.BiuUserSexMapper;
import com.zuosuo.biudb.provider.BiuUserSexProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserSexImpl")
public class BiuUserSexImpl extends AbstractImpl<BiuUserSexEntity, BiuUserSexMapper, BiuUserSexProvider> {
}
