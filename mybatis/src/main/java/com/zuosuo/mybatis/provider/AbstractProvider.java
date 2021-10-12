package com.zuosuo.mybatis.provider;

import com.zuosuo.mybatis.entity.BaseEntity;

public abstract class AbstractProvider<T extends BaseEntity> implements BaseProvider<T> {
    private String table, primary = "id";

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String getPrimary() {
        return primary;
    }

    @Override
    public void setPrimary(String primary) {
        this.primary = primary;
    }

}
