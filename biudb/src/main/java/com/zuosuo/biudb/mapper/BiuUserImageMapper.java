package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserImageEntity;
import com.zuosuo.biudb.provider.BiuUserImageProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserImageMapper extends BaseMapper<BiuUserImageEntity> {

    @InsertProvider(type = BiuUserImageProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserImageEntity entity);
    @UpdateProvider(type = BiuUserImageProvider.class, method = "update")
    long update(BiuUserImageEntity entity);
    @UpdateProvider(type = BiuUserImageProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserImageProvider.class, method = "delete")
    long delete(BiuUserImageEntity entity);
    @UpdateProvider(type = BiuUserImageProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserImageProvider.class, method = "restore")
    long restore(BiuUserImageEntity entity);
    @UpdateProvider(type = BiuUserImageProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserImageProvider.class, method = "single")
    BiuUserImageEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserImageProvider.class, method = "list")
    List<BiuUserImageEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserImageProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
