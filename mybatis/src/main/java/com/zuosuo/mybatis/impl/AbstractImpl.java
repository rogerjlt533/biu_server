package com.zuosuo.mybatis.impl;

import com.zuosuo.mybatis.entity.BaseEntity;
import com.zuosuo.mybatis.mapper.BaseMapper;
import com.zuosuo.mybatis.provider.AbstractProvider;
import com.zuosuo.mybatis.provider.CheckStatusEnum;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("AbstractImpl")
public abstract class AbstractImpl<T extends BaseEntity, M extends BaseMapper, P extends AbstractProvider> {
    private M mapper;
    private P provider;

    public M getMapper() {
        return mapper;
    }

    @Resource
    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    @Resource
    public void setProvider(P provider) {
        this.provider = provider;
    }

    public T insert(T entity) {
        getMapper().insert(entity);
        return entity;
    }

    public long update(T entity) {
        return getMapper().update(entity);
    }

    public long modify(ProviderOption options) {
        return getMapper().modify(options);
    }

    public long delete(T entity) {
        return getMapper().delete(entity);
    }

    public long destroy(ProviderOption options) {
        return getMapper().destroy(options);
    }

    public long restore(T entity) {
        return getMapper().restore(entity);
    }

    public long restoreAll(ProviderOption options) {
        return getMapper().restoreAll(options);
    }

    public T single(ProviderOption options) {
        return (T) getMapper().single(options);
    }

    public T find(long id) {
        return find(id, CheckStatusEnum.NORMAL.getValue());
    }

    public T find(long id, int status) {
        ProviderOption options = new ProviderOption();
        options.addCondition(provider.getPrimary(), id);
        options.setStatus(status);
        return single(options);
    }

    public List<T> list(ProviderOption options) {
        return getMapper().list(options);
    }

}
