package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserFriendEntity;
import com.zuosuo.biudb.provider.BiuUserFriendProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserFriendMapper extends BaseMapper<BiuUserFriendEntity> {

    @InsertProvider(type = BiuUserFriendProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserFriendEntity entity);
    @UpdateProvider(type = BiuUserFriendProvider.class, method = "update")
    long update(BiuUserFriendEntity entity);
    @UpdateProvider(type = BiuUserFriendProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendProvider.class, method = "delete")
    long delete(BiuUserFriendEntity entity);
    @UpdateProvider(type = BiuUserFriendProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendProvider.class, method = "restore")
    long restore(BiuUserFriendEntity entity);
    @UpdateProvider(type = BiuUserFriendProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendProvider.class, method = "single")
    BiuUserFriendEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendProvider.class, method = "list")
    List<BiuUserFriendEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
