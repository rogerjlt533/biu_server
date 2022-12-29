package com.zuosuo.treehole.task;

import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("ProcessNoteCacheTask")
public class ProcessNoteCacheTask {

    @Autowired
    private BiuRedisFactory biuRedisFactory;
    @Autowired
    private UserProcessor userProcessor;

    @Scheduled(fixedDelay = 100)
    public void execute() {
        String key = TaskOption.NOTE_CACHE_PROCESS.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        if (!operator.getRedisTool().exists(key)) {
            return ;
        }
        long size = operator.size(key);
        for (long i = 0; i < size; i++) {
            String noteId = operator.leftPop(key);
            userProcessor.setNoteCache(Long.valueOf(noteId));
        }
    }
}
