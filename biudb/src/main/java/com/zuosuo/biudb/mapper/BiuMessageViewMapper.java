package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuMessageViewEntity;
import com.zuosuo.biudb.provider.BiuMessageViewProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuMessageViewMapper extends BaseMapper<BiuMessageViewEntity> {

    @InsertProvider(type = BiuMessageViewProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuMessageViewEntity entity);
    @UpdateProvider(type = BiuMessageViewProvider.class, method = "update")
    long update(BiuMessageViewEntity entity);
    @UpdateProvider(type = BiuMessageViewProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuMessageViewProvider.class, method = "delete")
    long delete(BiuMessageViewEntity entity);
    @UpdateProvider(type = BiuMessageViewProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuMessageViewProvider.class, method = "restore")
    long restore(BiuMessageViewEntity entity);
    @UpdateProvider(type = BiuMessageViewProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuMessageViewProvider.class, method = "single")
    BiuMessageViewEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuMessageViewProvider.class, method = "list")
    List<BiuMessageViewEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuMessageViewProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
