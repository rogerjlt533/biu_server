package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserBlacklistEntity;
import com.zuosuo.biudb.mapper.BiuUserBlacklistMapper;
import com.zuosuo.biudb.provider.BiuUserBlacklistProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserBlacklistImpl")
public class BiuUserBlacklistImpl extends AbstractImpl<BiuUserBlacklistEntity, BiuUserBlacklistMapper, BiuUserBlacklistProvider> {
}
