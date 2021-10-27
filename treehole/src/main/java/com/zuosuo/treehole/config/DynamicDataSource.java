package com.zuosuo.treehole.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceOption.DataBaseItem dataBaseItem = DataSourceOption.getDataBaseItem();
        return dataBaseItem;
    }
}
