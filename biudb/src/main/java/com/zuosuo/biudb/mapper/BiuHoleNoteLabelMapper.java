package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuHoleNoteLabelEntity;
import com.zuosuo.biudb.provider.BiuHoleNoteLabelProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuHoleNoteLabelMapper extends BaseMapper<BiuHoleNoteLabelEntity> {

    @InsertProvider(type = BiuHoleNoteLabelProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuHoleNoteLabelEntity entity);
    @UpdateProvider(type = BiuHoleNoteLabelProvider.class, method = "update")
    long update(BiuHoleNoteLabelEntity entity);
    @UpdateProvider(type = BiuHoleNoteLabelProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @DeleteProvider(type = BiuHoleNoteLabelProvider.class, method = "delete")
    long delete(BiuHoleNoteLabelEntity entity);
    @DeleteProvider(type = BiuHoleNoteLabelProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteLabelProvider.class, method = "single")
    BiuHoleNoteLabelEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteLabelProvider.class, method = "list")
    List<BiuHoleNoteLabelEntity> list(@Param("options") ProviderOption options);
}
