package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserFriendCommunicateLogEntity;
import com.zuosuo.biudb.provider.BiuUserFriendCommunicateLogProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserFriendCommunicateLogMapper extends BaseMapper<BiuUserFriendCommunicateLogEntity> {

    @InsertProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserFriendCommunicateLogEntity entity);
    @UpdateProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "update")
    long update(BiuUserFriendCommunicateLogEntity entity);
    @UpdateProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "delete")
    long delete(BiuUserFriendCommunicateLogEntity entity);
    @UpdateProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "restore")
    long restore(BiuUserFriendCommunicateLogEntity entity);
    @UpdateProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "single")
    BiuUserFriendCommunicateLogEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "list")
    List<BiuUserFriendCommunicateLogEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendCommunicateLogProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
