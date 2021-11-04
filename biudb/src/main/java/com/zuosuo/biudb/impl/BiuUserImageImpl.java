package com.zuosuo.biudb.impl;

import com.zuosuo.biudb.entity.BiuUserImageEntity;
import com.zuosuo.biudb.mapper.BiuUserImageMapper;
import com.zuosuo.biudb.provider.BiuUserImageProvider;
import com.zuosuo.mybatis.impl.AbstractImpl;
import org.springframework.stereotype.Component;

@Component("BiuUserImageImpl")
public class BiuUserImageImpl extends AbstractImpl<BiuUserImageEntity, BiuUserImageMapper, BiuUserImageProvider> {
}
