package com.zuosuo.mybatis.mapper;

import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {

    void insert(T entity);
    long update(T entity);
    long modify(@Param("options") ProviderOption options);
    long delete(T entity);
    long destroy(@Param("options") ProviderOption options);
    long restore(T entity);
    long restoreAll(@Param("options") ProviderOption options);
    T single(@Param("options") ProviderOption options);
    List<T> list(@Param("options") ProviderOption options);
}
