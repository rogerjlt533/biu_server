package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserFriendMessageEntity;
import com.zuosuo.biudb.mapper.BiuUserFriendMessageMapper;
import com.zuosuo.biudb.provider.BiuUserFriendMessageProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserFriendMessageImpl")
public class BiuUserFriendMessageImpl extends AbstractImpl<BiuUserFriendMessageEntity, BiuUserFriendMessageMapper, BiuUserFriendMessageProvider> {
}
