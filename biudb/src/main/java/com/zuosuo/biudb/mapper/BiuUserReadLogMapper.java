package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserReadLogEntity;
import com.zuosuo.biudb.entity.BiuUserSexEntity;
import com.zuosuo.biudb.provider.BiuUserReadLogProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserReadLogMapper extends BaseMapper<BiuUserReadLogEntity> {

    @InsertProvider(type = BiuUserReadLogProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserSexEntity entity);
    @UpdateProvider(type = BiuUserReadLogProvider.class, method = "update")
    long update(BiuUserSexEntity entity);
    @UpdateProvider(type = BiuUserReadLogProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserReadLogProvider.class, method = "delete")
    long delete(BiuUserSexEntity entity);
    @UpdateProvider(type = BiuUserReadLogProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserReadLogProvider.class, method = "restore")
    long restore(BiuUserSexEntity entity);
    @UpdateProvider(type = BiuUserReadLogProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserReadLogProvider.class, method = "single")
    BiuUserReadLogEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserReadLogProvider.class, method = "list")
    List<BiuUserReadLogEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserReadLogProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
