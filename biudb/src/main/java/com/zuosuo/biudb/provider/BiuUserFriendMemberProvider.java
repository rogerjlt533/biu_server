package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserFriendMemberEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserFriendMemberProvider")
public class BiuUserFriendMemberProvider extends AbstractSoftDeleteProvider<BiuUserFriendMemberEntity> {
    public BiuUserFriendMemberProvider() {
        setTable(BiuTableEnum.USER_FRIEND_MEMBER.getValue());
    }
}
