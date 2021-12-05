package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserFriendCommunicateEntity;
import com.zuosuo.biudb.provider.BiuUserFriendCommunicateProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserFriendCommunicateMapper extends BaseMapper<BiuUserFriendCommunicateEntity> {

    @InsertProvider(type = BiuUserFriendCommunicateProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserFriendCommunicateEntity entity);
    @UpdateProvider(type = BiuUserFriendCommunicateProvider.class, method = "update")
    long update(BiuUserFriendCommunicateEntity entity);
    @UpdateProvider(type = BiuUserFriendCommunicateProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendCommunicateProvider.class, method = "delete")
    long delete(BiuUserFriendCommunicateEntity entity);
    @UpdateProvider(type = BiuUserFriendCommunicateProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendCommunicateProvider.class, method = "restore")
    long restore(BiuUserFriendCommunicateEntity entity);
    @UpdateProvider(type = BiuUserFriendCommunicateProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendCommunicateProvider.class, method = "single")
    BiuUserFriendCommunicateEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendCommunicateProvider.class, method = "list")
    List<BiuUserFriendCommunicateEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendCommunicateProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
