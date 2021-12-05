package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserFriendCommunicateEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserFriendCommunicateProvider")
public class BiuUserFriendCommunicateProvider extends AbstractSoftDeleteProvider<BiuUserFriendCommunicateEntity> {
    public BiuUserFriendCommunicateProvider() {
        setTable(BiuTableEnum.USER_FRIEND_COMMUNICATE.getValue());
    }
}
