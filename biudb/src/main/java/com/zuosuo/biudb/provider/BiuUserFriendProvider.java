package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserFriendEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserFriendProvider")
public class BiuUserFriendProvider extends AbstractSoftDeleteProvider<BiuUserFriendEntity> {
    public BiuUserFriendProvider() {
        setTable(BiuTableEnum.USER_FRIEND.getValue());
    }
}
