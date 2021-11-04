package com.zuosuo.biudb.provider;

import com.zuosuo.biudb.config.BiuTableEnum;
import com.zuosuo.biudb.entity.BiuUserImageEntity;
import com.zuosuo.mybatis.provider.AbstractSoftDeleteProvider;
import org.springframework.stereotype.Component;

@Component("BiuUserImageProvider")
public class BiuUserImageProvider extends AbstractSoftDeleteProvider<BiuUserImageEntity> {
    public BiuUserImageProvider() {
        setTable(BiuTableEnum.USER_IMAGE.getValue());
    }
}
