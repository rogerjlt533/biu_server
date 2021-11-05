package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    public Map<String, Object> getUserView(long id) {
        String sql = "select * from biu_user_views where id=#id;".replace("#id", String.valueOf(id));
        return biuDbFactory.getUserDbFactory().getBiuUserImpl().executeRow(sql);
    }
}
