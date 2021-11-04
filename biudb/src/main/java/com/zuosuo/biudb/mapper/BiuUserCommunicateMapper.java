package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserCommunicateEntity;
import com.zuosuo.biudb.provider.BiuUserCommunicateProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuUserCommunicateMapper extends BaseMapper<BiuUserCommunicateEntity> {

    @InsertProvider(type = BiuUserCommunicateProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserCommunicateEntity entity);
    @UpdateProvider(type = BiuUserCommunicateProvider.class, method = "update")
    long update(BiuUserCommunicateEntity entity);
    @UpdateProvider(type = BiuUserCommunicateProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserCommunicateProvider.class, method = "delete")
    long delete(BiuUserCommunicateEntity entity);
    @UpdateProvider(type = BiuUserCommunicateProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserCommunicateProvider.class, method = "restore")
    long restore(BiuUserCommunicateEntity entity);
    @UpdateProvider(type = BiuUserCommunicateProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserCommunicateProvider.class, method = "single")
    BiuUserCommunicateEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserCommunicateProvider.class, method = "list")
    List<BiuUserCommunicateEntity> list(@Param("options") ProviderOption options);
}
