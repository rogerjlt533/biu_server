package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserInterestEntity;
import com.zuosuo.biudb.provider.BiuUserInterestProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserInterestMapper extends BaseMapper<BiuUserInterestEntity> {

    @InsertProvider(type = BiuUserInterestProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserInterestEntity entity);
    @UpdateProvider(type = BiuUserInterestProvider.class, method = "update")
    long update(BiuUserInterestEntity entity);
    @UpdateProvider(type = BiuUserInterestProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserInterestProvider.class, method = "delete")
    long delete(BiuUserInterestEntity entity);
    @UpdateProvider(type = BiuUserInterestProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserInterestProvider.class, method = "restore")
    long restore(BiuUserInterestEntity entity);
    @UpdateProvider(type = BiuUserInterestProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserInterestProvider.class, method = "single")
    BiuUserInterestEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserInterestProvider.class, method = "list")
    List<BiuUserInterestEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserInterestProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
