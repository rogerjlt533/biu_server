package com.zuosuo.treehole;

import com.zuosuo.biudb.entity.BiuHoleNoteEntity;
import com.zuosuo.biudb.entity.BiuMessageEntity;
import com.zuosuo.biudb.entity.BiuUserIndexViewEntity;
import com.zuosuo.biudb.entity.BiuUserViewEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.mybatis.provider.CheckStatusEnum;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.config.SystemOption;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.processor.WechatProcessor;
import com.zuosuo.treehole.service.KeywordService;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.task.UserCollectInput;
import com.zuosuo.treehole.tool.HashTool;
import com.zuosuo.treehole.tool.QiniuTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class TreeholeApplicationTests {

    @Autowired
    private MiniWechatConfig miniWechatConfig;
    @Autowired
    private WechatProcessor loginProcessor;
    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private BiuRedisFactory biuRedisFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private QiniuTool qiniuTool;
    @Autowired
    private HashTool hashTool;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private UserProcessor userProcessor;

    @Test
    void contextLoads() {
//        processUserIndex();
        System.out.println(new Date().getTime());
//        userService.syncUserIndex(232);
//        userService.removeFriendMessage(884);
//        ProviderOption option = new ProviderOption();
//        option.setStatus(CheckStatusEnum.DELETED.getValue());
//        option.setColumns("id,deleted_at");
//        List<BiuHoleNoteEntity> notes = biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().list(option);
//        notes.forEach(item -> {
//            System.out.println("note_id: " + item.getId());
//            System.out.println("deleted_at: " + item.getDeletedAt());
//            userProcessor.removeHoleNoteMessage(item.getId());
//            userProcessor.removeHoleNoteComment(item.getId());
//        });
//
//        String test = "笔友【heel中文】测试";
//        System.out.println(test);
//        System.out.println(test.replaceAll("【[^】]*】", "【测试】"));
//        System.out.println(hashTool.getHashids(4).encode(9));
//        System.out.println(userService.parseImage("upload/d842ee3d20d04288ac23de0a248ecf89.png"));
//        System.out.println(JsonTool.toJson(keywordService.getKeywordList()));
//        userService.addUserMessage(0, 229, BiuMessageEntity.PUBLIC_NOTICE, 0, "欢迎加入BIU笔友", "", SystemOption.REGISTER_NOTICE_BANNER.getValue(), Arrays.asList(SystemOption.REGISTER_NOTICE_IMAGE.getValue()).stream().collect(Collectors.toList()));
//        userService.addUserMessage(0, 229, BiuMessageEntity.PUBLIC_NOTICE, 0, "平台注意事项", "", SystemOption.REGISTER_NOTICE_PLAT_BANNER.getValue(), Arrays.asList(SystemOption.REGISTER_NOTICE_PLAT_IMAGE.getValue()).stream().collect(Collectors.toList()));
//        try(BufferedReader reader = new BufferedReader(new FileReader("D:\\area.txt"))) {
//            String line = reader.readLine();
//            while (line != null) {
//                System.out.println(line);
//                line = reader.readLine();
//            }
//        } catch (IOException e) {
//
//        }
//        System.out.println(qiniuTool.getConfig().getBucket());
//        System.out.println(qiniuTool.getUploadToken());

//        BiuUserViewEntity entity = biuDbFactory.getUserDbFactory().getBiuUserViewImpl().find(1);
//        System.out.println(JsonTool.toJson(entity));
//        ProviderOption option = new ProviderOption();
//        List<BiuUserViewEntity> list = biuDbFactory.getUserDbFactory().getBiuUserViewImpl().list(option);
//        System.out.println(JsonTool.toJson(list));

//        boolean result = userService.getUserCollectService().pushUserCollect(1, 2, new Date());
//        System.out.println(result);
//        biuRedisFactory.getBiuRedisTool().
//        String url = "https://www.baidu.com";
//        FuncResult result = HttpTool.get(url);
//        System.out.println(result.isStatus());
//        HttpEntity entity = (HttpEntity) result.getResult();
//        String content = null;
//        try {
//            content = new BufferedReader(new InputStreamReader(entity.getContent())).lines().parallel().collect(Collectors.joining("\n"));
//        } catch (IOException e) {
//        }
//        System.out.println(content);
    }

    private void processUserIndex() {
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
        list.forEach(item -> userService.execSyncUserIndex(item));
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
