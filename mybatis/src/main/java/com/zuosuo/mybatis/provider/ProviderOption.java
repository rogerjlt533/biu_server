package com.zuosuo.mybatis.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderOption {

    private Map<String, Object> options;
    private List<String> attributes;
    private List<String> conditions;
    private String columns = "*";
    private int status = CheckStatusEnum.NORMAL.getValue();
    private List<String> orderbys;
    private long offset;
    private int limit;
    private boolean usePager;

    public ProviderOption() {
        options = new HashMap<>();
        attributes = new ArrayList<>();
        conditions = new ArrayList<>();
        orderbys = new ArrayList<>();
        offset = limit = 0;
    }

    public ProviderOption(List<String> conditions) {
        this.conditions = conditions;
        options = new HashMap<>();
        attributes = new ArrayList<>();
        orderbys = new ArrayList<>();
        offset = limit = 0;
    }

    public ProviderOption setOption(String name, Object value) {
        options.put(name, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getOption(String name, Class<T> className) {
        if (options.get(name) == null) {
            return null;
        }
        return (T) options.get(name);
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public ProviderOption addCondition(String value) {
        conditions.add(value);
        return this;
    }

    public ProviderOption addCondition(String name, int value) {
        conditions.add(name + "=" + value);
        return this;
    }

    public ProviderOption addCondition(String name, long value) {
        conditions.add(name + "=" + value);
        return this;
    }

    public ProviderOption addCondition(String name, double value) {
        conditions.add(name + "=" + value);
        return this;
    }

    public ProviderOption addCondition(String name, String value) {
        return addCondition(name, value, false);
    }

    public ProviderOption addCondition(String name, String value, boolean direct) {
        String content = value;
        if (!direct) {
            content = "'" + value + "'";
        }
        attributes.add(name + "=" + content);
        return this;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public ProviderOption setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ProviderOption setAttribute(String value) {
        attributes.add(value);
        return this;
    }

    public ProviderOption setAttribute(String name, int value) {
        attributes.add(name + "=" + value);
        return this;
    }

    public ProviderOption setAttribute(String name, long value) {
        attributes.add(name + "=" + value);
        return this;
    }

    public ProviderOption setAttribute(String name, double value) {
        attributes.add(name + "=" + value);
        return this;
    }

    public ProviderOption setAttribute(String name, String value, boolean direct) {
        String content = value;
        if (!direct) {
            content = "'" + value + "'";
        }
        attributes.add(name + "=" + content);
        return this;
    }

    public ProviderOption setAttribute(String name, String value) {
        return setAttribute(name, value, false);
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public ProviderOption setColumns(String columns) {
        this.columns = columns;
        return this;
    }

    public String getColumns() {
        return columns;
    }

    public ProviderOption setOrderbys(List<String> orderbys) {
        this.orderbys = orderbys;
        return this;
    }

    public ProviderOption addOrderby(String value) {
        orderbys.add(value);
        return this;
    }

    public List<String> getOrderbys() {
        return orderbys;
    }

    public long getOffset() {
        return offset;
    }

    public ProviderOption setOffset(long offset) {
        this.offset = offset;
        return this;
    }

    public ProviderOption setOffset(long page, int size) {
        this.limit = size;
        this.offset = (page - 1) * size;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public ProviderOption setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public boolean isUsePager() {
        return usePager;
    }

    public void setUsePager(boolean usePager) {
        this.usePager = usePager;
    }
}
