package com.zuosuo.mybatis.mapper;

import com.zuosuo.mybatis.provider.ProviderOption;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
    Map<String, Object> count(@Param("options") ProviderOption options);
    @Select({"${sql}"})
    @ResultType(Map.class)
    Map<String, Object> executeRow(@Param("sql") String sql);
    @Select({"${sql}"})
    @ResultType(List.class)
    List<Map<String, Object>> executeList(@Param("sql") String sql);
}
