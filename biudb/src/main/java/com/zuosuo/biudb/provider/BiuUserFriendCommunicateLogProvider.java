package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserFriendCommunicateLogEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserFriendCommunicateLogProvider")
public class BiuUserFriendCommunicateLogProvider extends AbstractSoftDeleteProvider<BiuUserFriendCommunicateLogEntity> {
    public BiuUserFriendCommunicateLogProvider() {
        setTable(BiuTableEnum.USER_FRIEND_COMMUNICATE_LOG.getValue());
    }
}
