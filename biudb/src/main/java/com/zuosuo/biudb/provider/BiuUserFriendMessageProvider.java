package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserFriendMemberEntity;
import com.zuosuo.biudb.entity.BiuUserFriendMessageEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserFriendMessageProvider")
public class BiuUserFriendMessageProvider extends AbstractSoftDeleteProvider<BiuUserFriendMessageEntity> {
    public BiuUserFriendMessageProvider() {
        setTable(BiuTableEnum.USER_FRIEND_MESSAGE.getValue());
    }
}
