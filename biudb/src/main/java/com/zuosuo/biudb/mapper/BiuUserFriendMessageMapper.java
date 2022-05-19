package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserFriendMessageEntity;
import com.zuosuo.biudb.provider.BiuUserFriendMessageProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserFriendMessageMapper extends BaseMapper<BiuUserFriendMessageEntity> {

    @InsertProvider(type = BiuUserFriendMessageProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserFriendMessageEntity entity);
    @UpdateProvider(type = BiuUserFriendMessageProvider.class, method = "update")
    long update(BiuUserFriendMessageEntity entity);
    @UpdateProvider(type = BiuUserFriendMessageProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendMessageProvider.class, method = "delete")
    long delete(BiuUserFriendMessageEntity entity);
    @UpdateProvider(type = BiuUserFriendMessageProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendMessageProvider.class, method = "restore")
    long restore(BiuUserFriendMessageEntity entity);
    @UpdateProvider(type = BiuUserFriendMessageProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendMessageProvider.class, method = "single")
    BiuUserFriendMessageEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendMessageProvider.class, method = "list")
    List<BiuUserFriendMessageEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendMessageProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
