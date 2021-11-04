package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserCommunicateEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserCommunicateProvider")
public class BiuUserCommunicateProvider extends AbstractSoftDeleteProvider<BiuUserCommunicateEntity> {
    public BiuUserCommunicateProvider() {
        setTable(BiuTableEnum.USER_COMMUNICATE.getValue());
    }
}
