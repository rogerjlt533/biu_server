package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserSexEntity;
import com.zuosuo.biudb.provider.BiuUserSexProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuUserSexMapper extends BaseMapper<BiuUserSexEntity> {

    @InsertProvider(type = BiuUserSexProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserSexEntity entity);
    @UpdateProvider(type = BiuUserSexProvider.class, method = "update")
    long update(BiuUserSexEntity entity);
    @UpdateProvider(type = BiuUserSexProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserSexProvider.class, method = "delete")
    long delete(BiuUserSexEntity entity);
    @UpdateProvider(type = BiuUserSexProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserSexProvider.class, method = "restore")
    long restore(BiuUserSexEntity entity);
    @UpdateProvider(type = BiuUserSexProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserSexProvider.class, method = "single")
    BiuUserSexEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserSexProvider.class, method = "list")
    List<BiuUserSexEntity> list(@Param("options") ProviderOption options);
}
