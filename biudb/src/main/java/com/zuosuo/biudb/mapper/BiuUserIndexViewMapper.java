package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserIndexViewEntity;
import com.zuosuo.biudb.provider.BiuUserIndexViewProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserIndexViewMapper extends BaseMapper<BiuUserIndexViewEntity> {

    @InsertProvider(type = BiuUserIndexViewProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserIndexViewEntity entity);
    @UpdateProvider(type = BiuUserIndexViewProvider.class, method = "update")
    long update(BiuUserIndexViewEntity entity);
    @UpdateProvider(type = BiuUserIndexViewProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserIndexViewProvider.class, method = "delete")
    long delete(BiuUserIndexViewEntity entity);
    @UpdateProvider(type = BiuUserIndexViewProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserIndexViewProvider.class, method = "single")
    BiuUserIndexViewEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserIndexViewProvider.class, method = "list")
    List<BiuUserIndexViewEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserIndexViewProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
