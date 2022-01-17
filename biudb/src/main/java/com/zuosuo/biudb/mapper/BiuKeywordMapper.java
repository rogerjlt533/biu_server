package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuKeywordEntity;
import com.zuosuo.biudb.provider.BiuKeywordProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuKeywordMapper extends BaseMapper<BiuKeywordEntity> {

    @InsertProvider(type = BiuKeywordProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuKeywordEntity entity);
    @UpdateProvider(type = BiuKeywordProvider.class, method = "update")
    long update(BiuKeywordEntity entity);
    @UpdateProvider(type = BiuKeywordProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @DeleteProvider(type = BiuKeywordProvider.class, method = "delete")
    long delete(BiuKeywordEntity entity);
    @DeleteProvider(type = BiuKeywordProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuKeywordProvider.class, method = "single")
    BiuKeywordEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuKeywordProvider.class, method = "list")
    List<BiuKeywordEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuKeywordProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
