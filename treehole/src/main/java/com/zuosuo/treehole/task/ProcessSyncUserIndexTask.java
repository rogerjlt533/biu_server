package com.zuosuo.treehole.task;

import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("ProcessSyncUserIndexTask")
public class ProcessSyncUserIndexTask {

    @Autowired
    private BiuRedisFactory biuRedisFactory;
    @Autowired
    private UserService userService;

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        String key = TaskOption.USER_INDEX_SYNC.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        if (!operator.getRedisTool().exists(key)) {
            return ;
        }
        long size = operator.size(key);
        for (long i = 0; i < size; i++) {
            String userId = operator.leftPop(key);
            System.out.println("sync user:" + userId);
            userService.execSyncUserIndex(Long.valueOf(userId));
        }
    }
}
