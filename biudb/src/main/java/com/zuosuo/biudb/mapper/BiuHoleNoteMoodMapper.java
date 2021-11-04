package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuHoleNoteMoodEntity;
import com.zuosuo.biudb.provider.BiuHoleNoteMoodProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuHoleNoteMoodMapper extends BaseMapper<BiuHoleNoteMoodEntity> {

    @InsertProvider(type = BiuHoleNoteMoodProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuHoleNoteMoodEntity entity);
    @UpdateProvider(type = BiuHoleNoteMoodProvider.class, method = "update")
    long update(BiuHoleNoteMoodEntity entity);
    @UpdateProvider(type = BiuHoleNoteMoodProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @DeleteProvider(type = BiuHoleNoteMoodProvider.class, method = "delete")
    long delete(BiuHoleNoteMoodEntity entity);
    @DeleteProvider(type = BiuHoleNoteMoodProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteMoodProvider.class, method = "single")
    BiuHoleNoteMoodEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteMoodProvider.class, method = "list")
    List<BiuHoleNoteMoodEntity> list(@Param("options") ProviderOption options);
}
