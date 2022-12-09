package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.entity.BiuWechatFilterTraceEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuWechatFilterTraceProvider")
public class BiuWechatFilterTraceProvider extends AbstractSoftDeleteProvider<BiuWechatFilterTraceEntity> {
    public BiuWechatFilterTraceProvider() {
        setTable(BiuTableEnum.WECHAT_FILTER_TRACE.getValue());
    }
}
