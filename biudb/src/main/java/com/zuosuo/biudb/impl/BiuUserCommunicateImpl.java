package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserCommunicateEntity;
import com.zuosuo.biudb.mapper.BiuUserCommunicateMapper;
import com.zuosuo.biudb.provider.BiuUserCommunicateProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserCommunicateImpl")
public class BiuUserCommunicateImpl extends AbstractImpl<BiuUserCommunicateEntity, BiuUserCommunicateMapper, BiuUserCommunicateProvider> {
}
