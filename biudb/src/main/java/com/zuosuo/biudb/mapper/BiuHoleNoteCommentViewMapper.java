package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuHoleNoteCommentViewEntity;
import com.zuosuo.biudb.provider.BiuHoleNoteCommentViewProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuHoleNoteCommentViewMapper extends BaseMapper<BiuHoleNoteCommentViewEntity> {

    @InsertProvider(type = BiuHoleNoteCommentViewProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuHoleNoteCommentViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteCommentViewProvider.class, method = "update")
    long update(BiuHoleNoteCommentViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteCommentViewProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuHoleNoteCommentViewProvider.class, method = "delete")
    long delete(BiuHoleNoteCommentViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteCommentViewProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuHoleNoteCommentViewProvider.class, method = "restore")
    long restore(BiuHoleNoteCommentViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteCommentViewProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteCommentViewProvider.class, method = "single")
    BiuHoleNoteCommentViewEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteCommentViewProvider.class, method = "list")
    List<BiuHoleNoteCommentViewEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteCommentViewProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
