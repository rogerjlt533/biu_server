package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.BiuUserCollectEntity;
import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.task.UserCollectInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("UserCollectService")
public class UserCollectService {

    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private BiuRedisFactory biuRedisFactory;

    public boolean pushUserCollect(BiuUserEntity user, BiuUserEntity relate, Date time) {
        if (user == null || relate == null) {
            return false;
        }
        String key = TaskOption.USER_COLLECT.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        operator.rightPush(key, JsonTool.toJson(new UserCollectInput(user.getId(), relate.getId(), time)));
        return true;
    }

    public boolean pushUserCollect(long userId, long relateId, Date time) {
        BiuUserEntity user = biuDbFactory.getUserDbFactory().getBiuUserImpl().find(userId);
        if (user == null) {
            return false;
        }
        BiuUserEntity relate = biuDbFactory.getUserDbFactory().getBiuUserImpl().find(relateId);
        if (relate == null) {
            return false;
        }
        return pushUserCollect(user, relate, time);
    }

    public boolean isCollected(long userId, long relateId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("relate_id", relateId);
        BiuUserCollectEntity entity = biuDbFactory.getUserDbFactory().getBiuUserCollectImpl().single(option);
        return entity != null ? true : false;
    }
}
