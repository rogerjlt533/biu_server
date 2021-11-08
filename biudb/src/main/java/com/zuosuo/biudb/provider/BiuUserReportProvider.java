package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserReportEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserReportProvider")
public class BiuUserReportProvider extends AbstractSoftDeleteProvider<BiuUserReportEntity> {
    public BiuUserReportProvider() {
        setTable(BiuTableEnum.USER_REPORT.getValue());
    }
}
