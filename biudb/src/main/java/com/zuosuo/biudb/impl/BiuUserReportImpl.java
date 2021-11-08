package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserReportEntity;
import com.zuosuo.biudb.mapper.BiuUserReportMapper;
import com.zuosuo.biudb.provider.BiuUserReportProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserReportImpl")
public class BiuUserReportImpl extends AbstractImpl<BiuUserReportEntity, BiuUserReportMapper, BiuUserReportProvider> {
}
