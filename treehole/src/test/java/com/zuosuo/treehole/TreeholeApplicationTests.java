package com.zuosuo.treehole;

import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.processor.WechatProcessor;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.task.UserCollectInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

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

    @Test
    void contextLoads() {
//        boolean result = userService.getUserCollectService().pushUserCollect(1, 2, new Date());
//        System.out.println(result);
        String sql = "select u.*, \n" +
                "(select GROUP_CONCAT(distinct ui.interest_id separator ',') from biu_user_interests ui where u.id=ui.user_id and ui.use_type=1 and ISNULL(ui.deleted_at)) as interest_id,\n" +
                "(select GROUP_CONCAT(distinct uc.com_method separator ',') from biu_user_communicates uc where u.id=uc.user_id and uc.use_type=1 and ISNULL(uc.deleted_at)) as com_method\n" +
                "from biu_users u\n" +
                "where u.id=1 and ISNULL(u.deleted_at);".replace("#id", String.valueOf(1));
        System.out.println(sql);
        Map<String, Object> row = biuDbFactory.getUserDbFactory().getBiuUserImageImpl().executeRow(sql);
        System.out.println(JsonTool.toJson(row));
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

}
