package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("UserService")
public class UserService {

    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private UserCollectService userCollectService;

    public UserCollectService getUserCollectService() {
        return userCollectService;
    }

    public BiuUserEntity getUserByOpenid(String openid) {
        ProviderOption option = new ProviderOption();
        option.addCondition("openid", openid);
        return biuDbFactory.getUserDbFactory().getBiuUserImpl().single(option);
    }

    public BiuUserEntity find(long id) {
        return biuDbFactory.getUserDbFactory().getBiuUserImpl().find(id);
    }

    // 获取用户视图
    public BiuUserViewEntity getUserView(long id) {
        return biuDbFactory.getUserDbFactory().getBiuUserViewImpl().find(id);
    }

    // 设置用户更新时间
    public void setUserSortTime(long id) {
        ProviderOption option = new ProviderOption();
        option.addCondition("id", id);
        option.addCondition("DATE_FORMAT(NOW(),'%Y-%m-%d')!=DATE_FORMAT(sort_time,'%Y-%m-%d')");
        option.setAttribute("'sort_time'", TimeTool.formatDate(new Date()));
        biuDbFactory.getUserDbFactory().getBiuUserImpl().modify(option);
    }

    public List<String> getUserImageList(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", BiuUserImageEntity.USE_TYPE_INTRODUCE);
        option.addOrderby("sort_index asc");
        List<BiuUserImageEntity> list = biuDbFactory.getUserDbFactory().getBiuUserImageImpl().list(option);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().map(BiuUserImageEntity::getFile).collect(Collectors.toList());
    }

    public List<String> getUserInterestList(long userId) {
        List<BiuUserInterestEntity> list = biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().list(new ProviderOption(new ArrayList<String>(){{
            add("user_id=" + userId);
            add("user_type=" + BiuUserInterestEntity.USE_TYPE_SELF );
        }}));
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> relates = list.stream().map(item -> String.valueOf(item.getInterestId())).collect(Collectors.toList());
        List<BiuInterestEntity> interests = biuDbFactory.getUserDbFactory().getBiuInterestImpl().list(new ProviderOption(new ArrayList<String>(){{
            add("id in ("  + String.join(",", relates) +")");
        }}));
        if (interests.isEmpty()) {
            return new ArrayList<>();
        }
        return interests.stream().map(BiuInterestEntity::getTag).collect(Collectors.toList());
    }

    public boolean isCollected(long userId, long relateId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("relate_id", relateId);
        BiuUserCollectEntity entity = biuDbFactory.getUserDbFactory().getBiuUserCollectImpl().single(option);
        return entity != null ? true : false;
    }
}
