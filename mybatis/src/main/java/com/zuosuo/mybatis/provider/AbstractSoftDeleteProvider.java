package com.zuosuo.mybatis.provider;

import com.zuosuo.mybatis.entity.BaseEntity;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public abstract class AbstractSoftDeleteProvider<T extends BaseEntity> extends AbstractProvider<T> {

    @Override
    public boolean softDelete() {
        return true;
    }

    @Override
    public String delete(T entity) {
        return new SQL() {{
            UPDATE(getTable());
            SET(ProviderTool.softDelete());
            WHERE(getPrimary()+"=#{"+getPrimary()+"}", ProviderTool.normal());
        }}.toString();
    }

    @Override
    public String destroy(Map<String, Object> params) {
        ProviderOption options = (ProviderOption) params.get("options");
        options.getConditions().add(ProviderTool.normal());
        String[] conditions = options.getConditions().toArray(new String[]{});
        return new SQL() {{
            UPDATE(getTable());
            SET(ProviderTool.softDelete());
            if (conditions.length > 0) {
                WHERE(conditions);
            }
        }}.toString();
    }

    protected void softDeleteCondition(ProviderOption options) {
        if (options.getStatus() == CheckStatusEnum.NORMAL.getValue()) {
            options.getConditions().add(ProviderTool.normal());
        } else if(options.getStatus() == CheckStatusEnum.DELETED.getValue()) {
            options.getConditions().add(ProviderTool.deleted());
        }
    }

    @Override
    public String single(Map<String, Object> params) {
        ProviderOption options = (ProviderOption) params.get("options");
        softDeleteCondition(options);
        return super.single(params);
    }

    @Override
    public String list(Map<String, Object> params) {
        ProviderOption options = (ProviderOption) params.get("options");
        softDeleteCondition(options);
        return super.list(params);
    }
}
