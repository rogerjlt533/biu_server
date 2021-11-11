package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuMessageEntity;
import com.zuosuo.biudb.provider.BiuMessageProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuMessageMapper extends BaseMapper<BiuMessageEntity> {

    @InsertProvider(type = BiuMessageProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuMessageEntity entity);
    @UpdateProvider(type = BiuMessageProvider.class, method = "update")
    long update(BiuMessageEntity entity);
    @UpdateProvider(type = BiuMessageProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuMessageProvider.class, method = "delete")
    long delete(BiuMessageEntity entity);
    @UpdateProvider(type = BiuMessageProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuMessageProvider.class, method = "restore")
    long restore(BiuMessageEntity entity);
    @UpdateProvider(type = BiuMessageProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuMessageProvider.class, method = "single")
    BiuMessageEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuMessageProvider.class, method = "list")
    List<BiuMessageEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuMessageProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
