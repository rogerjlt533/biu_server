package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuMessageViewEntity;
import com.zuosuo.biudb.mapper.BiuMessageViewMapper;
import com.zuosuo.biudb.provider.BiuMessageViewProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuMessageIViewImpl")
public class BiuMessageViewImpl extends AbstractImpl<BiuMessageViewEntity, BiuMessageViewMapper, BiuMessageViewProvider> {
}
