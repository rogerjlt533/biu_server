package com.zuosuo.treehole.task;

import com.zuosuo.biudb.entity.BiuUserIndexViewEntity;
import com.zuosuo.biudb.entity.BiuUserViewEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("SyncUserIndexViewTask")
public class SyncUserIndexViewTask {

    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 4 * * ?")
    public void execute() {
        System.out.println("start sync");
        List<Long> viewList = getViewList();
        Map<Long, Long> indexList = getIndexList();
        List<Long> delList = new ArrayList<>();
        indexList.forEach((id, userId) -> {
            if (!viewList.contains(userId)) {
                delList.add(id);
                System.out.println("del:" + id);
            }
        });
        removeIndexList(delList);
        addIndexList(viewList.stream().filter(item -> !delList.contains(item)).collect(Collectors.toList()));
    }

    private List<Long> getViewList() {
        ProviderOption option = new ProviderOption();
        option.setColumns("id");
        option.addCondition("use_status", BiuUserViewEntity.USER_AVAIL_STATUS);
        option.addCondition("search_status", BiuUserViewEntity.SEARCH_OPEN_STATUS);
        option.addCondition("LENGTH(title)");
        option.addCondition("LENGTH(introduce)");
        option.addCondition("LENGTH(self_communicate)");
        option.addCondition("LENGTH(search_communicate)");
        option.addCondition("LENGTH(search_sex)");
        option.addCondition("sort_time>='" + TimeTool.formatDate(TimeTool.getOffsetDate(new Date(), new DiscTime().setDay(-14))) + "'");
        List<BiuUserViewEntity> users = biuDbFactory.getUserDbFactory().getBiuUserViewImpl().list(option);
        return users.stream().map(item -> item.getId()).collect(Collectors.toList());
    }

    private Map<Long, Long> getIndexList() {
        ProviderOption option = new ProviderOption();
        option.setColumns("id, user_id");
        List<BiuUserIndexViewEntity> users = biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().list(option);
        return users.stream().collect(Collectors.toMap(BiuUserIndexViewEntity::getId, BiuUserIndexViewEntity::getUserId));
    }

    private void addIndexList(List<Long> list) {
        System.out.println(list.size());
        list.forEach(item -> userService.syncUserIndex(item));
    }

    private void removeIndexList(List<Long> list) {
        if (list.isEmpty()) {
            return ;
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("id in (" + list.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
        biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
    }
}
