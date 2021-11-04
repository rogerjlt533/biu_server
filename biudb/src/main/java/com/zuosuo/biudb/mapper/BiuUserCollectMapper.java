package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserCollectEntity;
import com.zuosuo.biudb.provider.BiuUserCollectProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuUserCollectMapper extends BaseMapper<BiuUserCollectEntity> {

    @InsertProvider(type = BiuUserCollectProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserCollectEntity entity);
    @UpdateProvider(type = BiuUserCollectProvider.class, method = "update")
    long update(BiuUserCollectEntity entity);
    @UpdateProvider(type = BiuUserCollectProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserCollectProvider.class, method = "delete")
    long delete(BiuUserCollectEntity entity);
    @UpdateProvider(type = BiuUserCollectProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserCollectProvider.class, method = "restore")
    long restore(BiuUserCollectEntity entity);
    @UpdateProvider(type = BiuUserCollectProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserCollectProvider.class, method = "single")
    BiuUserCollectEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserCollectProvider.class, method = "list")
    List<BiuUserCollectEntity> list(@Param("options") ProviderOption options);
}
