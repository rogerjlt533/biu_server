package com.zuosuo.biudb.mapper;

import com.zuosuo.biudb.entity.BiuHoleNoteEntity;
import com.zuosuo.biudb.provider.BiuHoleNoteProvider;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BiuHoleNoteMapper extends BaseMapper<BiuHoleNoteEntity> {

    @InsertProvider(type = BiuHoleNoteProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(BiuHoleNoteEntity entity);
    @UpdateProvider(type = BiuHoleNoteProvider.class, method = "update")
    long update(BiuHoleNoteEntity entity);
    @UpdateProvider(type = BiuHoleNoteProvider.class, method = "modify")
    long modify(@Param("options") ProviderOption options);
    @DeleteProvider(type = BiuHoleNoteProvider.class, method = "delete")
    long delete(BiuHoleNoteEntity entity);
    @DeleteProvider(type = BiuHoleNoteProvider.class, method = "destroy")
    long destroy(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteProvider.class, method = "single")
    BiuHoleNoteEntity single(@Param("options") ProviderOption options);
    @SelectProvider(type = BiuHoleNoteProvider.class, method = "list")
    List<BiuHoleNoteEntity> list(@Param("options") ProviderOption options);
}
