package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserBlacklistEntity;
import com.zuosuo.biudb.provider.BiuUserBlacklistProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserBlacklistMapper extends BaseMapper<BiuUserBlacklistEntity> {

    @InsertProvider(type = BiuUserBlacklistProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserBlacklistEntity entity);
    @UpdateProvider(type = BiuUserBlacklistProvider.class, method = "update")
    long update(BiuUserBlacklistEntity entity);
    @UpdateProvider(type = BiuUserBlacklistProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserBlacklistProvider.class, method = "delete")
    long delete(BiuUserBlacklistEntity entity);
    @UpdateProvider(type = BiuUserBlacklistProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserBlacklistProvider.class, method = "restore")
    long restore(BiuUserBlacklistEntity entity);
    @UpdateProvider(type = BiuUserBlacklistProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserBlacklistProvider.class, method = "single")
    BiuUserBlacklistEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserBlacklistProvider.class, method = "list")
    List<BiuUserBlacklistEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserBlacklistProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
