package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserFavorEntity;
import com.zuosuo.biudb.provider.BiuUserFavorProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuUserFavorMapper extends BaseMapper<BiuUserFavorEntity> {

    @InsertProvider(type = BiuUserFavorProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserFavorEntity entity);
    @UpdateProvider(type = BiuUserFavorProvider.class, method = "update")
    long update(BiuUserFavorEntity entity);
    @UpdateProvider(type = BiuUserFavorProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFavorProvider.class, method = "delete")
    long delete(BiuUserFavorEntity entity);
    @UpdateProvider(type = BiuUserFavorProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFavorProvider.class, method = "restore")
    long restore(BiuUserFavorEntity entity);
    @UpdateProvider(type = BiuUserFavorProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFavorProvider.class, method = "single")
    BiuUserFavorEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFavorProvider.class, method = "list")
    List<BiuUserFavorEntity> list(@Param("options") ProviderOption options);
}
