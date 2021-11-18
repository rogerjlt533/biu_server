package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.BiuAreaEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.result.AreaInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AreaService")
public class AreaService {

    @Autowired
    private BiuDbFactory biuDbFactory;

    public String getArea(String code) {
        if (code == null) {
            return "";
        }
        if (code.trim().isEmpty()) {
            return "";
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("code", code.trim());
        BiuAreaEntity area = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
        if (area == null) {
            return "";
        }
        return area.getAreaName();
    }
}
