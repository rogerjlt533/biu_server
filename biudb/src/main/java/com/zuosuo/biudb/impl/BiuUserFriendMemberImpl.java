package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserFriendMemberEntity;
import com.zuosuo.biudb.mapper.BiuUserFriendMemberMapper;
import com.zuosuo.biudb.provider.BiuUserFriendMemberProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserFriendMemberImpl")
public class BiuUserFriendMemberImpl extends AbstractImpl<BiuUserFriendMemberEntity, BiuUserFriendMemberMapper, BiuUserFriendMemberProvider> {
}
