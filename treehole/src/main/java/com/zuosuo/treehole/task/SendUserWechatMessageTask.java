package com.zuosuo.treehole.task;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.cache.redis.ValueOperator;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.config.TemplateOption;
import com.zuosuo.treehole.processor.WechatProcessor;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.tool.WechatTool;
import com.zuosuo.wechat.WechatMiniTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("SendUserWechatMessageTask")
public class SendUserWechatMessageTask {

    public static final long COMMENT_TYPE = 1;
    public static final long APPLY_FRIEND_TYPE = 2;
    public static final long PRIVATE_MESSAGE_TYPE = 3;
    public static final long LETTER_REPLY_TYPE = 4;

    @Autowired
    private BiuRedisFactory biuRedisFactory;
    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private MiniWechatConfig miniWechatConfig;
    @Autowired
    private WechatProcessor wechatProcessor;
    @Autowired
    private WechatTool wechatTool;

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        String key = TaskOption.USER_WECHAT_MESSAGE.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        if (!operator.getRedisTool().exists(key)) {
            return ;
        }
        long size = operator.size(key);
        for (long i = 0; i < size; i++) {
            String message_value = operator.leftPop(key);
            String[] values = message_value.split("_");
            long message_type = Long.valueOf(values[0]);
            if (message_type == COMMENT_TYPE) {
                sendCommentMessage(Long.valueOf(values[1]), Long.valueOf(values[2]), Long.valueOf(values[3]));
            } else if (message_type == APPLY_FRIEND_TYPE) {
                sendFriendApplyMessage(Long.valueOf(values[1]), Long.valueOf(values[2]), Long.valueOf(values[3]));
            } else if (message_type == PRIVATE_MESSAGE_TYPE) {
                sendPrivateMessage(Long.valueOf(values[1]), Long.valueOf(values[2]), Long.valueOf(values[3]));
            } else if (message_type == LETTER_REPLY_TYPE) {
                sendLetterReplyMessage(Long.valueOf(values[1]), Long.valueOf(values[2]), Long.valueOf(values[3]));
            }
        }
    }

    private String getAccessToken() {
        return String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
    }

    /**
     * 新的评论通知
     * @param receiverId
     * @param sendUser
     * @param commentId
     */
    private void sendCommentMessage(long receiverId, long sendUser, long commentId) {
//        BiuUserEntity receiver = userService.find(receiverId);
//        BiuUserEntity sender = userService.find(sendUser);
//        BiuHoleNoteCommentEntity commentEntity = biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().find(commentId);
//        Map<String, Object> params = new HashMap<>();
//        params.put("page", "pages/index/index");
//        Map<String, Object> data = new HashMap<>();
//        data.put("thing3", new HashMap<String, String>(){{put("value", sender.getPenName());}});
//        data.put("time2", new HashMap<String, String>(){{put("value", TimeTool.formatDate(commentEntity.getCreatedAt(), "yyyy/MM/dd HH:mm"));}});
//        data.put("thing4", new HashMap<String, String>(){{put("value", "关闭订阅消息：我的-设置");}});
//        params.put("data", data);
//        WechatMiniTool.sendMiniTemplateMessage(getAccessToken(), receiver.getOpenid(), TemplateOption.NEW_COMMENT_TEMPLATE.getId(), params);
    }

    /**
     * 新好友申请通知
     * @param receiverId
     * @param applyUser
     * @param friendId
     */
    private void sendFriendApplyMessage(long receiverId, long applyUser, long friendId) {
        BiuUserEntity receiver = userService.find(receiverId);
        BiuUserEntity applyer = userService.find(applyUser);
        BiuUserFriendEntity friendEntity = biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().find(friendId);
        Map<String, Object> params = new HashMap<>();
        params.put("page", "pages/index/index");
        Map<String, Object> data = new HashMap<>();
        data.put("thing1", new HashMap<String, String>(){{put("value", applyer.getPenName());}});
        data.put("time2", new HashMap<String, String>(){{put("value", TimeTool.formatDate(friendEntity.getCreatedAt(), "yyyy/MM/dd HH:mm"));}});
        params.put("data", data);
        wechatTool.sendMiniTemplateMessage(getAccessToken(), receiver.getOpenid(), TemplateOption.FRIEND_APPLY_TEMPLATE.getId(), params);
    }

    /**
     * 未读私信通知
     * @param receiverId
     * @param sendUser
     * @param messageId
     */
    private void sendPrivateMessage(long receiverId, long sendUser, long messageId) {
        BiuMessageEntity messageEntity = biuDbFactory.getUserDbFactory().getBiuMessageImpl().find(messageId);
        String date = TimeTool.formatDate(messageEntity.getCreatedAt(), "yyyyMMdd");
        String key = String.format("%s:%s:%d:%d", TaskOption.USER_WECHAT_PRIVATE_MESSAGE.getValue(), date, sendUser, receiverId);
        if (biuRedisFactory.getBiuRedisTool().exists(key)) {
            return ;
        }
        BiuUserEntity receiver = userService.find(receiverId);
        BiuUserEntity sender = userService.find(sendUser);
        Map<String, Object> params = new HashMap<>();
        params.put("page", "pages/index/index");
        Map<String, Object> data = new HashMap<>();
        data.put("thing1", new HashMap<String, String>(){{put("value", sender.getPenName());}});
        data.put("thing2", new HashMap<String, String>(){{put("value", messageEntity.getContent());}});
        data.put("time3", new HashMap<String, String>(){{put("value", TimeTool.formatDate(messageEntity.getCreatedAt(), "yyyy/MM/dd HH:mm"));}});
        params.put("data", data);
        wechatTool.sendMiniTemplateMessage(getAccessToken(), receiver.getOpenid(), TemplateOption.WAITING_PRIVATE_MESSAGE_TEMPLATE.getId(), params);
        ValueOperator operator = biuRedisFactory.getBiuRedisTool().getValueOperator();
        operator.set(key, 1, 86400);
    }

    /**
     * 发送信件回复通知
     * @param receiverId
     * @param sendUser
     * @param logId
     */
    private void sendLetterReplyMessage(long receiverId, long sendUser, long logId) {
        BiuUserFriendCommunicateLogEntity logEntity = biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().find(logId);
        BiuUserEntity receiver = userService.find(receiverId);
        BiuUserEntity sender = userService.find(sendUser);
        Map<String, Object> params = new HashMap<>();
        params.put("page", "pages/index/index");
        Map<String, Object> data = new HashMap<>();
        data.put("thing1", new HashMap<String, String>(){{put("value", sender.getPenName());}});
        data.put("time2", new HashMap<String, String>(){{put("value", TimeTool.formatDate(logEntity.getCreatedAt(), "yyyy/MM/dd HH:mm"));}});
        data.put("thing3", new HashMap<String, String>(){{put("value", "您的笔友已发出邮件，请查收");}});
        params.put("data", data);
        wechatTool.sendMiniTemplateMessage(getAccessToken(), receiver.getOpenid(), TemplateOption.LETTER_REPLY_TEMPLATE.getId(), params);
    }
}
