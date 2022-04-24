package com.zuosuo.mybatis.provider;

import com.zuosuo.mybatis.annotation.PropertyOption;
import com.zuosuo.mybatis.annotation.PropertyTool;
import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BaseProvider<T extends BaseEntity> {
    String getTable();
    void setTable(String table);
    String getPrimary();
    void setPrimary(String primary);

    default boolean softDelete() {
        return false;
    }

    default String insert(T entity) {
        List<PropertyOption> optionList = PropertyTool.getPropertyList(entity);
        List<String> columns = new ArrayList<>();
        List<String> properties = new ArrayList<>();
        for (PropertyOption option: optionList) {
            columns.add("`"+option.getColumn()+"`");
            properties.add("#{"+option.getProperty()+"}");
        }
        ProviderTool.setTimestamp(columns, properties);
        return new SQL() {{
            INSERT_INTO(getTable());
            VALUES(String.join(",", columns), String.join(",", properties));
        }}.toString();
    }

    default String update(T entity) {
        List<PropertyOption> optionList = PropertyTool.getPropertyList(entity);
        return new SQL() {{
            UPDATE(getTable());
            for (PropertyOption option: optionList) {
                SET("`"+option.getColumn()+"`=#{"+option.getProperty()+"}");
            }
            SET("`updated_at`=NOW()");
            if (softDelete()) {
                WHERE(getPrimary()+"=#{"+getPrimary()+"}", ProviderTool.normal());
            } else {
                WHERE(getPrimary()+"=#{"+getPrimary()+"}");
            }
        }}.toString();
    }

    default String modify(Map<String, Object> params) {
        ProviderOption options = (ProviderOption) params.get("options");
        String[] attributes = options.getAttributes().toArray(new String[]{});
        if (softDelete()) {
            options.getConditions().add(ProviderTool.normal());
        }
        String[] conditions = options.getConditions().toArray(new String[]{});
        String sql = new SQL() {{
            UPDATE(getTable());
            SET(attributes);
            if (conditions.length > 0) {
                WHERE(conditions);
            }
        }}.toString();
        if (options.isWriteLog()) {
            System.out.println(sql);
        }
        return sql;
    }

    default String delete(T entity) {
        return new SQL() {{
            DELETE_FROM(getTable());
            WHERE(getPrimary()+"=#{"+getPrimary()+"}");
        }}.toString();
    }

    default String destroy(Map<String, Object> params) {
        ProviderOption options = (ProviderOption) params.get("options");
        String[] conditions = options.getConditions().toArray(new String[]{});
        return new SQL() {{
            DELETE_FROM(getTable());
            if (conditions.length > 0) {
                WHERE(conditions);
            }
        }}.toString();
    }

    default String restore(T entity) {
        return "";
    }

    default String restoreAll(Map<String, Object> params) {
        return "";
    }

    default String single(Map<String, Object> params) {
        ProviderOption options = (ProviderOption) params.get("options");
        String[] conditions = options.getConditions().toArray(new String[]{});
        String[] orderbyList = options.getOrderbys().toArray(new String[]{});
        String sql = new SQL() {{
            SELECT(options.getColumns());
            FROM(getTable());
            if (conditions.length > 0) {
                WHERE(conditions);
            }
            if (orderbyList != null && orderbyList.length > 0) {
                ORDER_BY(orderbyList);
            }
            LIMIT(1);
        }}.toString();
//        System.out.println(sql);
        return sql;
    }

    default String list(Map<String, Object> params) {
        ProviderOption options = (ProviderOption) params.get("options");
        String[] conditions = options.getConditions().toArray(new String[]{});
        String[] orderbyList = options.getOrderbys().toArray(new String[]{});
        String sql = new SQL() {{
            SELECT(options.getColumns());
            FROM(getTable());
            if (conditions.length > 0) {
                WHERE(conditions);
            }
            if (orderbyList != null && orderbyList.length > 0) {
                ORDER_BY(orderbyList);
            }
            if (options.isUsePager()) {
                OFFSET(options.getOffset());
                LIMIT(options.getLimit());
            }
        }}.toString();
        if (options.isWriteLog()) {
            System.out.println(sql);
        }
        return sql;
    }

    default String count(Map<String, Object> params) {
        ProviderOption options = (ProviderOption) params.get("options");
        String[] conditions = options.getConditions().toArray(new String[]{});
        String sql = new SQL() {{
            SELECT("count(*) as ts_count");
            FROM(getTable());
            if (conditions.length > 0) {
                WHERE(conditions);
            }
        }}.toString();
//        System.out.println(sql);
        return sql;
    }
}
