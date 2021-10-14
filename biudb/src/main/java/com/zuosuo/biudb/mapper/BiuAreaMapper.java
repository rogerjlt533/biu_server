package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuAreaEntity;
import com.zuosuo.biudb.provider.BiuAreaProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuAreaMapper extends BaseMapper<BiuAreaEntity> {

    @InsertProvider(type = BiuAreaProvider.class, method = "insert")
    void insert(BiuAreaEntity entity);
    @UpdateProvider(type = BiuAreaProvider.class, method = "update")
    long update(BiuAreaEntity entity);
    @UpdateProvider(type = BiuAreaProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @DeleteProvider(type = BiuAreaProvider.class, method = "delete")
    long delete(BiuAreaEntity entity);
    @DeleteProvider(type = BiuAreaProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuAreaProvider.class, method = "single")
    BiuAreaEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuAreaProvider.class, method = "list")
    List<BiuAreaEntity> list(@Param("options") ProviderOption options);
}
