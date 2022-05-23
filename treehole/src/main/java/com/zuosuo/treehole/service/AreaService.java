package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.BiuAreaEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.result.AreaInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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

    public BiuAreaEntity getParent(BiuAreaEntity area) {
        if (area == null) {
            return null;
        }
        if (area.getParentCode().isEmpty()) {
            return null;
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("code", area.getParentCode());
        return biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
    }

    public boolean verifyAreaList(List<String> list) {
        if (list == null) {
            return true;
        } else if (list.isEmpty()) {
            return true;
        }
        BiuAreaEntity parent = null;
        for (String code: list) {
            ProviderOption option = new ProviderOption();
            option.addCondition("code", code);
            BiuAreaEntity area = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
            if (area == null) {
                return false;
            }
            if (parent != null && !area.getParentCode().equals(parent.getCode())) {
                return false;
            }
            parent = area;
        }
        return true;
    }

    /**
     * 修正地区前缀
     * @param province
     * @param city
     * @param country
     * @return
     */
    public Map<String, String> correctArea(String province, String city, String country) {
        province = province != null?  province: "";
        city = city != null?  city: "";
        country = country != null?  country: "";
        List<String> prefix = new ArrayList<>();
        if (!country.isEmpty()) {
            ProviderOption option = new ProviderOption();
            option.addCondition("code", country);
            BiuAreaEntity countryEntity = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
            if (countryEntity != null) {
                prefix.add(countryEntity.getAreaName());
                BiuAreaEntity cityEntity = getParent(countryEntity);
                if (cityEntity != null) {
                    prefix.add(cityEntity.getAreaName());
                    BiuAreaEntity provinceEntity = getParent(cityEntity);
                    if (provinceEntity != null) {
                        prefix.add(provinceEntity.getAreaName());
                    }
                }
            }
        } else if(!city.isEmpty()) {
            ProviderOption option = new ProviderOption();
            option.addCondition("code", city);
            BiuAreaEntity cityEntity = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
            if (cityEntity != null) {
                prefix.add(cityEntity.getAreaName());
                BiuAreaEntity provinceEntity = getParent(cityEntity);
                if (provinceEntity != null) {
                    prefix.add(provinceEntity.getAreaName());
                }
            }
        } else if(!province.isEmpty()) {
            ProviderOption option = new ProviderOption();
            option.addCondition("code", province);
            BiuAreaEntity provinceEntity = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
            if (provinceEntity != null) {
                prefix.add(provinceEntity.getAreaName());
            }
        }
        Map<String, String> areas = new HashMap<>();
        Collections.reverse(prefix);
        areas.put("prefix", prefix.stream().collect(Collectors.joining("")));
        return areas;
    }
}
