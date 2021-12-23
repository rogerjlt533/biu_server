package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserReadLogEntity;
import com.zuosuo.biudb.mapper.BiuUserReadLogMapper;
import com.zuosuo.biudb.provider.BiuUserReadLogProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserReadLogImpl")
public class BiuUserReadLogImpl extends AbstractImpl<BiuUserReadLogEntity, BiuUserReadLogMapper, BiuUserReadLogProvider> {
}
