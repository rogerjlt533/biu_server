/**
 * 处理用户收藏
 */
package com.zuosuo.treehole.task;

import com.zuosuo.biudb.entity.BiuUserCollectEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.impl.BiuUserCollectImpl;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.mybatis.provider.CheckStatusEnum;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("ProcessUserCancelCollectTask")
public class ProcessUserCancelCollectTask {

    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private BiuRedisFactory biuRedisFactory;
    @Autowired
    private UserService userService;

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        String key = TaskOption.USER_CANCEL.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        long size = operator.size(key);
        for (long i = 0; i < size; i++) {
            String content = operator.leftPop(key);
            UserCollectInput input = JsonTool.parse(content, UserCollectInput.class);
            process(input);
        }
    }

    private void process(UserCollectInput input) {
        BiuUserCollectImpl impl = biuDbFactory.getUserDbFactory().getBiuUserCollectImpl();
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", input.getUserId());
        option.addCondition("relate_id", input.getRelateId());
        BiuUserCollectEntity collect = impl.single(option);
        if (collect != null) {
            impl.delete(collect);
        }
        userService.syncUserIndex(input.getRelateId());
    }
}
