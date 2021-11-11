package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuHoleNoteViewEntity;
import com.zuosuo.biudb.provider.BiuHoleNoteViewProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiuHoleNoteViewMapper extends BaseMapper<BiuHoleNoteViewEntity> {

    @InsertProvider(type = BiuHoleNoteViewProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuHoleNoteViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteViewProvider.class, method = "update")
    long update(BiuHoleNoteViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteViewProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuHoleNoteViewProvider.class, method = "delete")
    long delete(BiuHoleNoteViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteViewProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @UpdateProvider(type = BiuHoleNoteViewProvider.class, method = "restore")
    long restore(BiuHoleNoteViewEntity entity);
    @UpdateProvider(type = BiuHoleNoteViewProvider.class, method = "restoreAll")
    long restoreAll(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteViewProvider.class, method = "single")
    BiuHoleNoteViewEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteViewProvider.class, method = "list")
    List<BiuHoleNoteViewEntity> list(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteViewProvider.class, method = "count")
    Map<String, Object> count(@Param("options") ProviderOption options);
}
