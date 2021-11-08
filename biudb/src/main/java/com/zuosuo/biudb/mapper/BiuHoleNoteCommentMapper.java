package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuHoleNoteCommentEntity;
import com.zuosuo.biudb.provider.BiuHoleNoteCommentProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuHoleNoteCommentMapper extends BaseMapper<BiuHoleNoteCommentEntity> {

    @InsertProvider(type = BiuHoleNoteCommentProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuHoleNoteCommentEntity entity);
    @UpdateProvider(type = BiuHoleNoteCommentProvider.class, method = "update")
    long update(BiuHoleNoteCommentEntity entity);
    @UpdateProvider(type = BiuHoleNoteCommentProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuHoleNoteCommentProvider.class, method = "delete")
    long delete(BiuHoleNoteCommentEntity entity);
    @UpdateProvider(type = BiuHoleNoteCommentProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuHoleNoteCommentProvider.class, method = "restore")
    long restore(BiuHoleNoteCommentEntity entity);
    @UpdateProvider(type = BiuHoleNoteCommentProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteCommentProvider.class, method = "single")
    BiuHoleNoteCommentEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteCommentProvider.class, method = "list")
    List<BiuHoleNoteCommentEntity> list(@Param("options") ProviderOption options);
}
