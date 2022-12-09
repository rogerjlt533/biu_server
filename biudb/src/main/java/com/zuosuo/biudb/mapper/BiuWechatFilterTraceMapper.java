package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuWechatFilterTraceEntity;
import com.zuosuo.biudb.provider.BiuWechatFilterTraceProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuWechatFilterTraceMapper extends BaseMapper<BiuWechatFilterTraceEntity> {

    @InsertProvider(type = BiuWechatFilterTraceProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuWechatFilterTraceEntity entity);
    @UpdateProvider(type = BiuWechatFilterTraceProvider.class, method = "update")
    long update(BiuWechatFilterTraceEntity entity);
    @UpdateProvider(type = BiuWechatFilterTraceProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuWechatFilterTraceProvider.class, method = "delete")
    long delete(BiuWechatFilterTraceEntity entity);
    @UpdateProvider(type = BiuWechatFilterTraceProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuWechatFilterTraceProvider.class, method = "restore")
    long restore(BiuWechatFilterTraceEntity entity);
    @UpdateProvider(type = BiuWechatFilterTraceProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuWechatFilterTraceProvider.class, method = "single")
    BiuWechatFilterTraceEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuWechatFilterTraceProvider.class, method = "list")
    List<BiuWechatFilterTraceEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuWechatFilterTraceProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
