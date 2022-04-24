package com.zuosuo.treehole.task;

import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.treehole.processor.CombineAccountProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AutoCombineAccountTask {

    @Autowired
    private CombineAccountProcessor combineAccountProcessor;
    @Autowired
    private BiuDbFactory biuDbFactory;

    @Scheduled(cron = "0 0 3 * * ?")
    public void execute() {
        String sql = "select * from biu_users where use_status=1 and ISNULL(deleted_at) GROUP BY openid HAVING count(*)>1;";
        List<Map<String, Object>> list = biuDbFactory.getUserDbFactory().getBiuUserImpl().executeList(sql);
        if (list.isEmpty()) {
            return ;
        }
        list.forEach(item -> combineAccountProcessor.combineUserByOpenId((String) item.get("openid")));
    }
}
