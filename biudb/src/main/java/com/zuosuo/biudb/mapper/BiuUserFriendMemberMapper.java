package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuUserFriendMemberEntity;
import com.zuosuo.biudb.provider.BiuUserFriendMemberProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuUserFriendMemberMapper extends BaseMapper<BiuUserFriendMemberEntity> {

    @InsertProvider(type = BiuUserFriendMemberProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuUserFriendMemberEntity entity);
    @UpdateProvider(type = BiuUserFriendMemberProvider.class, method = "update")
    long update(BiuUserFriendMemberEntity entity);
    @UpdateProvider(type = BiuUserFriendMemberProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendMemberProvider.class, method = "delete")
    long delete(BiuUserFriendMemberEntity entity);
    @UpdateProvider(type = BiuUserFriendMemberProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuUserFriendMemberProvider.class, method = "restore")
    long restore(BiuUserFriendMemberEntity entity);
    @UpdateProvider(type = BiuUserFriendMemberProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendMemberProvider.class, method = "single")
    BiuUserFriendMemberEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendMemberProvider.class, method = "list")
    List<BiuUserFriendMemberEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuUserFriendMemberProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
