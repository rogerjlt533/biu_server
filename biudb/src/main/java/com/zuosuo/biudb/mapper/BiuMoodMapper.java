package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuMoodEntity;
import com.zuosuo.biudb.provider.BiuMoodProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuMoodMapper extends BaseMapper<BiuMoodEntity> {

    @InsertProvider(type = BiuMoodProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuMoodEntity entity);
    @UpdateProvider(type = BiuMoodProvider.class, method = "update")
    long update(BiuMoodEntity entity);
    @UpdateProvider(type = BiuMoodProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @DeleteProvider(type = BiuMoodProvider.class, method = "delete")
    long delete(BiuMoodEntity entity);
    @DeleteProvider(type = BiuMoodProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuMoodProvider.class, method = "single")
    BiuMoodEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuMoodProvider.class, method = "list")
    List<BiuMoodEntity> list(@Param("options") ProviderOption options);
}
