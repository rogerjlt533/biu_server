package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuHoleNoteListViewEntity;
import com.zuosuo.biudb.provider.BiuHoleNoteListViewProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuHoleNoteListViewMapper extends BaseMapper<BiuHoleNoteListViewEntity> {

    @InsertProvider(type = BiuHoleNoteListViewProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuHoleNoteListViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteListViewProvider.class, method = "update")
    long update(BiuHoleNoteListViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteListViewProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuHoleNoteListViewProvider.class, method = "delete")
    long delete(BiuHoleNoteListViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteListViewProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuHoleNoteListViewProvider.class, method = "restore")
    long restore(BiuHoleNoteListViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteListViewProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteListViewProvider.class, method = "single")
    BiuHoleNoteListViewEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteListViewProvider.class, method = "list")
    List<BiuHoleNoteListViewEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteListViewProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
