package com.zuosuo.treehole;

import com.qiniu.util.Json;
import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.mybatis.provider.CheckStatusEnum;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.bean.NoteCommentGroupBean;
import com.zuosuo.treehole.bean.UserFriendMessageBean;
import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.config.SystemOption;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.processor.CombineAccountProcessor;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.processor.WechatProcessor;
import com.zuosuo.treehole.result.UserInterestResult;
import com.zuosuo.treehole.service.AreaService;
import com.zuosuo.treehole.service.KeywordService;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.task.UserCollectInput;
import com.zuosuo.treehole.tool.AddressTool;
import com.zuosuo.treehole.tool.HashTool;
import com.zuosuo.treehole.tool.QiniuTool;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
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
    @Autowired
    private CombineAccountProcessor combineAccountProcessor;
    @Autowired
    private AreaService areaService;

    @Test
    void contextLoads() {
//        processUserAddress();

//        NoteCommentGroupBean bean = new NoteCommentGroupBean();
//        bean.setComment_id("qVlZ");
//        FuncResult res = userProcessor.getCommentGroupList(232, bean);
//        System.out.println(JsonTool.toJson(res));


//        List<List<UserInterestResult>> result = userService.getGroupInterestList(232);
//        System.out.println(JsonTool.toJson(result));
//        System.out.println(userProcessor.decodeHash("6akJ"));
//        userProcessor.removeUserFriendSession(856,232);
//        Calendar date = Caslendar.getInstance();
//        String year = String.valueOf(date.get(Calendar.YEAR));
//        System.out.println(year);
//        FuncResult res = userProcessor.getFriendMessageUserList(856, 2, 2);
//        System.out.println(JsonTool.toJson(res));
//        FuncResult res = userProcessor.getUserFriendMessageList(232, 0, 0, 2, 1, 10);
//        System.out.println(JsonTool.toJson(res));
//        processUserFriendMessageRelate();
//        sendUpdateMessage();
        // 评论同步顶级评论
//        processCommentTop();
        // 笔友信息同步
//        processUserIsPen();

//        String users = JsonTool.toJson(userProcessor.getFriendMessageUserList(232));
//        System.out.println(users);
//        String sql = "select * from biu_users where use_status=1 and ISNULL(deleted_at) GROUP BY openid HAVING count(*)>1;";
//        List<Map<String, Object>> list = biuDbFactory.getUserDbFactory().getBiuUserImpl().executeList(sql);
//        if (list.isEmpty()) {
//            return ;
//        }
////        System.out.println(JsonTool.toJson(list));
//        list.forEach(item -> combineAccountProcessor.combineUserByOpenId((String) item.get("openid")));

//        String users = "234,334";
//        List<Long> userlist = Arrays.asList(users.split(",")).stream().map(value -> Long.valueOf(value)).collect(Collectors.toList());
//        System.out.println(userlist);
//        List<Map<String, Object>> list = biuDbFactory.getUserDbFactory().getBiuUserImpl().executeList("select * from biu_users where use_status=1 GROUP BY openid HAVING count(*) >1;");
//        System.out.println(list);
//        FuncResult result = userProcessor.getUserFriendMessageList(232, 324, 1, 20);
//        System.out.println(result.getResult());
//        userService.removeUserFriend(232);
//        testAddress();
//        processUserIndex();
//        UserFriendMessageBean bean = new UserFriendMessageBean();
//        bean.setContent("ttttt");
//        bean.setFriend("1234");
//        bean.setImages("1234,33333");
//        userProcessor.sendUserFriendMessage(232, 74, bean);
//        String users = "123,234";
//        long userId = 123;
//        System.out.println(Arrays.asList(users.split(",")).stream().filter(item -> !item.equals(String.valueOf(userId))).findFirst().get());
//        long friendMemberId = Arrays.asList(users.split(",")).stream().mapToLong(Long::getLong).filter(item -> item != userId).findFirst().getAsLong();
//        System.out.println(friendMemberId);
//        compressImage();
//        processUserZipcode();
//        processUserIndex();
//        System.out.println(new Date().getTime());
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

    /**
     * 批处理用户地址
     */
    private void processUserAddress() {
        ProviderOption option = new ProviderOption();
        List<BiuUserEntity> list = biuDbFactory.getUserDbFactory().getBiuUserImpl().list(option);
        list.forEach(user -> {
            String address = user.getAddress();
            if (!user.getProvince().isEmpty()) {
                user.setNation("1");
            }
            if (address.contains("北京市") || address.contains("天津市") || address.contains("重庆市") || address.contains("省") || address.contains("自治区") || address.contains("上海市") || address.contains("行政区") || address.contains("台湾")) {

            } else {
                String province = user.getProvince();
                String city = user.getCity();
                String country = user.getCountry();
                Map<String, String> result = areaService.correctArea(province, city, country);
                if (!result.get("prefix").isEmpty()) {
                    user.setAddress(result.get("prefix") + user.getAddress());
                    ProviderOption where = new ProviderOption();
                    where.addCondition("user_id", user.getId());
                    BiuUserIndexViewEntity view = biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().single(where);
                    if (view != null) {
                        view.setNation(user.getNation());
                        view.setAddress(user.getAddress());
                        biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().update(view);
                    }
                }
            }
            biuDbFactory.getUserDbFactory().getBiuUserImpl().update(user);
        });
    }

    private void processUserFriendMessageRelate() {
        ProviderOption option = new ProviderOption();
        option.addCondition("message_type", BiuMessageEntity.PRIVATE_MESSAGE);
        List<BiuMessageEntity> list = biuDbFactory.getUserDbFactory().getBiuMessageImpl().list(option);
        list.forEach(item -> {
            long sourceId = item.getSourceId();
            long destId = item.getDestId();
            long messageId = item.getId();
            Arrays.asList(sourceId, destId).forEach(userId -> {
                ProviderOption where = new ProviderOption();
                where.addCondition("user_id", userId);
                where.addCondition("message_id", messageId);
                BiuUserFriendMessageEntity relate = biuDbFactory.getUserDbFactory().getBiuUserFriendMessageImpl().single(where);
                if (relate == null) {
                    relate = new BiuUserFriendMessageEntity();
                    relate.setUserId(userId);
                    relate.setFriendId(item.getFriendId());
                    relate.setMessageId(messageId);
                    relate.setUsers(item.getUsers());
                    biuDbFactory.getUserDbFactory().getBiuUserFriendMessageImpl().insert(relate);
                }
            });
        });
    }

    private void sendUpdateMessage() {
        ProviderOption option = new ProviderOption();
        option.addCondition("use_status", 1);
        List<BiuUserEntity> users = biuDbFactory.getUserDbFactory().getBiuUserImpl().list(option);
        users.forEach(item -> {
            userService.addUserMessage(0, item.getId(), BiuMessageEntity.PUBLIC_UPDATE, 0, "平台重大更新", "同志们晚上咱们重大更新，各种完善需求，必须得停服才能更新，大约晚上（万一更新崩溃了，估计明个你们看还是老样子，幸运的话各种功能就都有了）\n" +
                    "祈祷中。。。");
        });
    }

    private void processUserIsPen() {
        ProviderOption option = new ProviderOption();
        option.addCondition("use_status", 1);
        option.addCondition("is_penuser", 1);
        List<BiuUserEntity> users = biuDbFactory.getUserDbFactory().getBiuUserImpl().list(option);
        users.forEach(item -> {
            userService.initUserPenStatus(item);
            userService.execSyncUserIndex(item.getId());
            System.out.println(item.getId());
        });
    }

    private void processCommentTop() {
        ProviderOption option = new ProviderOption();
        option.addCondition("comment_id", 0);
        List<BiuHoleNoteCommentEntity> list = biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().list(option);
        list.forEach(item -> {
            item.setTopComment(0);
            biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().update(item);
            System.out.println(item.getId() + ": " + item.getTopComment());
            processCommentTree(item, item.getId());
        });
    }

    private void processCommentTree(BiuHoleNoteCommentEntity comment, long top) {
        ProviderOption option = new ProviderOption();
        option.addCondition("comment_id", comment.getId());
        List<BiuHoleNoteCommentEntity> list = biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().list(option);
        list.forEach(item -> {
            item.setTopComment(top);
            biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().update(item);
            processCommentTree(item, top);
            System.out.println(item.getId() + ": " + item.getTopComment());
        });
    }

    private void testAddress() {
        String str1 = "山东省青岛市黄岛区长江路街道阿里山路103号益铭别墅7号别墅";
        System.out.println(AddressTool.addressResolution(str1));
    }

    private void compressImage() {
        String uuid = "tifa";
        String ext = "jpeg";
        File dir = new File("upload");
        if (!dir.exists()) {
            dir.mkdir();
        }
        String dest = String.join(".", dir.getAbsolutePath() + "/" + uuid, ext);
        try {
            Thumbnails.of(dest).size(750, 1000).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File destFile = new File(dest);
//        try {
////            file.transferTo(destFile);
//            Thumbnails.of(dest).size(750, 1000).toFile(dest);
//        } catch (IOException e) {
//            destFile.delete();
//            return new JsonDataResult<>("上传失败");
//        }
    }

    private void processUserZipcode() {
        List<BiuUserEntity> list = biuDbFactory.getUserDbFactory().getBiuUserImpl().list(new ProviderOption());
        list.forEach(item -> {
            item = userService.initUserZipcode(item);
            biuDbFactory.getUserDbFactory().getBiuUserImpl().update(item);
        });
    }

    private void processUserIndex() {
        System.out.println("start sync");
        ProviderOption option = new ProviderOption();
        option.addCondition("is_penuser", 1);
        option.setAttribute("pen_pub_msg", 1);
        option.setWriteLog(true);
        biuDbFactory.getUserDbFactory().getBiuUserImpl().modify(option);
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
