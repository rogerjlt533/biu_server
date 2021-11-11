package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserReportEntity;
import com.zuosuo.biudb.provider.BiuUserReportProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserReportMapper extends BaseMapper<BiuUserReportEntity> {

    @InsertProvider(type = BiuUserReportProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserReportEntity entity);
    @UpdateProvider(type = BiuUserReportProvider.class, method = "update")
    long update(BiuUserReportEntity entity);
    @UpdateProvider(type = BiuUserReportProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserReportProvider.class, method = "delete")
    long delete(BiuUserReportEntity entity);
    @UpdateProvider(type = BiuUserReportProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserReportProvider.class, method = "restore")
    long restore(BiuUserReportEntity entity);
    @UpdateProvider(type = BiuUserReportProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserReportProvider.class, method = "single")
    BiuUserReportEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserReportProvider.class, method = "list")
    List<BiuUserReportEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserReportProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
