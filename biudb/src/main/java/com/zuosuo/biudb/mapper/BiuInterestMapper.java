package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuInterestEntity;
import com.zuosuo.biudb.provider.BiuInterestProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuInterestMapper extends BaseMapper<BiuInterestEntity> {

    @InsertProvider(type = BiuInterestProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuInterestEntity entity);
    @UpdateProvider(type = BiuInterestProvider.class, method = "update")
    long update(BiuInterestEntity entity);
    @UpdateProvider(type = BiuInterestProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @DeleteProvider(type = BiuInterestProvider.class, method = "delete")
    long delete(BiuInterestEntity entity);
    @DeleteProvider(type = BiuInterestProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuInterestProvider.class, method = "single")
    BiuInterestEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuInterestProvider.class, method = "list")
    List<BiuInterestEntity> list(@Param("options") ProviderOption options);
}
