package com.zuosuo.treehole;

import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.processor.WechatProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TreeholeApplicationTests {

    @Autowired
    private MiniWechatConfig miniWechatConfig;
    @Autowired
    private WechatProcessor loginProcessor;

    @Test
    void contextLoads() {
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
