package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserBlacklistEntity;
import com.zuosuo.biudb.entity.BiuUserSexEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserBlacklistProvider")
public class BiuUserBlacklistProvider extends AbstractSoftDeleteProvider<BiuUserBlacklistEntity> {
    public BiuUserBlacklistProvider() {
        setTable(BiuTableEnum.USER_BLACKLIST.getValue());
    }
}
