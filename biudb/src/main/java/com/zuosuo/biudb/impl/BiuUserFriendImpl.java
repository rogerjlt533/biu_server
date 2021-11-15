package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserFriendEntity;
import com.zuosuo.biudb.mapper.BiuUserFriendMapper;
import com.zuosuo.biudb.provider.BiuUserFriendProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserFriendImpl")
public class BiuUserFriendImpl extends AbstractImpl<BiuUserFriendEntity, BiuUserFriendMapper, BiuUserFriendProvider> {
}
