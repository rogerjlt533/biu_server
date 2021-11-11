package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuLabelEntity;
import com.zuosuo.biudb.provider.BiuLabelProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuLabelMapper extends BaseMapper<BiuLabelEntity> {

    @InsertProvider(type = BiuLabelProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuLabelEntity entity);
    @UpdateProvider(type = BiuLabelProvider.class, method = "update")
    long update(BiuLabelEntity entity);
    @UpdateProvider(type = BiuLabelProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuLabelProvider.class, method = "delete")
    long delete(BiuLabelEntity entity);
    @UpdateProvider(type = BiuLabelProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuLabelProvider.class, method = "restore")
    long restore(BiuLabelEntity entity);
    @UpdateProvider(type = BiuLabelProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuLabelProvider.class, method = "single")
    BiuLabelEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuLabelProvider.class, method = "list")
    List<BiuLabelEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuLabelProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
