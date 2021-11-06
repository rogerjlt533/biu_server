package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserViewEntity;
import com.zuosuo.biudb.provider.BiuUserViewProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuUserViewMapper extends BaseMapper<BiuUserViewEntity> {

    @InsertProvider(type = BiuUserViewProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserViewEntity entity);
    @UpdateProvider(type = BiuUserViewProvider.class, method = "update")
    long update(BiuUserViewEntity entity);
    @UpdateProvider(type = BiuUserViewProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserViewProvider.class, method = "delete")
    long delete(BiuUserViewEntity entity);
    @UpdateProvider(type = BiuUserViewProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserViewProvider.class, method = "restore")
    long restore(BiuUserViewEntity entity);
    @UpdateProvider(type = BiuUserViewProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserViewProvider.class, method = "single")
    BiuUserViewEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserViewProvider.class, method = "list")
    List<BiuUserViewEntity> list(@Param("options") ProviderOption options);
}
