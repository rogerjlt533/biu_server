package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.provider.BiuUserProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuUserMapper extends BaseMapper<BiuUserEntity> {

    @InsertProvider(type = BiuUserProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserEntity entity);
    @UpdateProvider(type = BiuUserProvider.class, method = "update")
    long update(BiuUserEntity entity);
    @UpdateProvider(type = BiuUserProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserProvider.class, method = "delete")
    long delete(BiuUserEntity entity);
    @UpdateProvider(type = BiuUserProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserProvider.class, method = "restore")
    long restore(BiuUserEntity entity);
    @UpdateProvider(type = BiuUserProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserProvider.class, method = "single")
    BiuUserEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserProvider.class, method = "list")
    List<BiuUserEntity> list(@Param("options") ProviderOption options);
}
