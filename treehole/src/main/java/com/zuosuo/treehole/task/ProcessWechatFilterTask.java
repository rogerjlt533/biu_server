package com.zuosuo.treehole.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.processor.WechatProcessor;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.tool.QiniuTool;
import com.zuosuo.wechat.WechatMiniTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("ProcessWechatFilterTask")
public class ProcessWechatFilterTask {

    public static final int FILTER_MEDIA = 1;
    public static final int FILTER_CONTENT = 2;
    public static final int MEDIA_AUDIO_TYPE = 1;
    public static final int MEDIA_IMAGE_TYPE = 2;
    public static final int MEDIA_AVATOR_TYPE = 3;
    public static final int CONTENT_NOTE_TYPE = 1;
    public static final int CONTENT_COMMENT_TYPE = 2;
    public static final int CONTENT_MESSAGE_TYPE = 3;

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
    private QiniuTool qiniuTool;

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        String key = TaskOption.WECHAT_FILTER.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        if (!operator.getRedisTool().exists(key)) {
            return ;
        }
        long size = operator.size(key);
        for (long i = 0; i < size; i++) {
            String message_value = operator.leftPop(key);
            String[] values = message_value.split("_");
            long filter_type = Long.valueOf(values[0]);
            if (filter_type == FILTER_MEDIA) {
//                if (values.length == 4) {
//                    filterMedia(Long.valueOf(values[1]), Integer.valueOf(values[2]), Long.valueOf(values[3]));
//                } else if (values.length == 6) {
//                    filterMedia(Long.valueOf(values[1]), Integer.valueOf(values[2]), Long.valueOf(values[3]), Integer.valueOf(values[4]), Long.valueOf(values[4]));
//                }
            } else if (filter_type == FILTER_CONTENT) {
//                long userId = Long.valueOf(values[1]);
//                int use_type = Integer.valueOf(values[2]);
//                long entityId = Integer.valueOf(values[3]);
//                if (use_type == CONTENT_NOTE_TYPE) {
//                    filterNoteContent(userId, entityId);
//                } else if (use_type == CONTENT_COMMENT_TYPE) {
//                    filterCommentContent(userId, entityId);
//                } else if (use_type == CONTENT_MESSAGE_TYPE) {
//                    filterMessageContent(userId, entityId);
//                }
            }
        }
    }

//    private void filterMedia(long userId, int mediaType, long imageId) {
//        BiuUserEntity userEntity = userService.find(userId);
//        BiuUserImageEntity imageEntity = biuDbFactory.getUserDbFactory().getBiuUserImageImpl().find(imageId);
//        String token = String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
//        FuncResult result = WechatMiniTool.filterMedia(token, userEntity.getOpenid(), qiniuTool.getLink(imageEntity.getFile()), mediaType == MEDIA_AVATOR_TYPE ? 2 : mediaType, 2, 1);
//        if (!result.isStatus()) {
//            return ;
//        }
//        JSONObject object = (JSONObject) result.getResult();
//        if (object.containsKey("errcode") && object.getInteger("errcode") == 0 && object.containsKey("trace_id")) {
//            BiuWechatFilterTraceEntity entity = new BiuWechatFilterTraceEntity();
//            entity.setFilterType(ProcessWechatFilterTask.FILTER_MEDIA);
//            entity.setUseType(mediaType);
//            entity.setUserId(userId);
//            entity.setTraceId(object.getString("trace_id"));
//            entity.setImageId(imageId);
//            entity.setResult("");
//            entity.setDetail("");
//            biuDbFactory.getUserDbFactory().getBiuWechatFilterTraceImpl().insert(entity);
//        }
//    }

//    private void filterMedia(long userId, int mediaType, long imageId, int type, long objectId) {
//        BiuUserEntity userEntity = userService.find(userId);
//        BiuUserImageEntity imageEntity = biuDbFactory.getUserDbFactory().getBiuUserImageImpl().find(imageId);
//        String token = String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
//        FuncResult result = WechatMiniTool.filterMedia(token, userEntity.getOpenid(), qiniuTool.getLink(imageEntity.getFile()), mediaType, 2, 1);
//        if (!result.isStatus()) {
//            return ;
//        }
//        JSONObject object = (JSONObject) result.getResult();
//        if (object.containsKey("errcode") && object.getInteger("errcode") == 0 && object.containsKey("trace_id")) {
//            BiuWechatFilterTraceEntity entity = new BiuWechatFilterTraceEntity();
//            entity.setFilterType(ProcessWechatFilterTask.FILTER_MEDIA);
//            entity.setUseType(mediaType);
//            entity.setUserId(userId);
//            entity.setTraceId(object.getString("trace_id"));
//            entity.setImageId(imageId);
//            if (type == BiuUserImageEntity.USE_TYPE_NOTE) {
//                entity.setNoteId(objectId);
//            }
//            if (type == BiuUserImageEntity.USE_TYPE_NOTE_COMMENT) {
//                entity.setCommentId(objectId);
//            }
//            if (type == BiuUserImageEntity.USE_TYPE_MESSAGE) {
//                entity.setMessageId(objectId);
//            }
//            entity.setResult("");
//            entity.setDetail("");
//            biuDbFactory.getUserDbFactory().getBiuWechatFilterTraceImpl().insert(entity);
//        }
//    }

//    private void processFilterContentEntity(JSONObject object, long userId, int useType, long noteId, long commentId, long messageId) {
//        if (object.containsKey("errcode") && object.getInteger("errcode") == 0 && object.containsKey("trace_id")) {
//            String responseResult = object.containsKey("result") ? JsonTool.toJson(object.getJSONObject("result")) : "";
//            String responseDetail = object.containsKey("detail") ? JsonTool.toJson(object.getJSONArray("detail")) : "";
//            BiuWechatFilterTraceEntity entity = new BiuWechatFilterTraceEntity();
//            entity.setFilterType(ProcessWechatFilterTask.FILTER_CONTENT);
//            entity.setUseType(useType);
//            entity.setUserId(userId);
//            entity.setTraceId(object.getString("trace_id"));
//            if (noteId > 0) {
//                entity.setNoteId(noteId);
//            }
//            if (commentId > 0) {
//                entity.setCommentId(commentId);
//            }
//            if (messageId > 0) {
//                entity.setMessageId(messageId);
//            }
//            entity.setResult(responseResult);
//            entity.setDetail(responseDetail);
//            biuDbFactory.getUserDbFactory().getBiuWechatFilterTraceImpl().insert(entity);
//        }
//    }

//    private void filterNoteContent(long userId, long noteId) {
//        BiuUserEntity userEntity = userService.find(userId);
//        BiuHoleNoteEntity entity = biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().find(noteId);
//        String token = String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
//        FuncResult result = WechatMiniTool.filterContent(token, userEntity.getOpenid(), 1, 2, entity.getContent(), null);
//        if (!result.isStatus()) {
//            return ;
//        }
//        processFilterContentEntity((JSONObject) result.getResult(), userId, ProcessWechatFilterTask.CONTENT_NOTE_TYPE, noteId, 0, 0);
//    }

//    private void filterCommentContent(long userId, long commentId) {
//        BiuUserEntity userEntity = userService.find(userId);
//        BiuHoleNoteCommentEntity entity = biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().find(commentId);
//        String token = String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
//        FuncResult result = WechatMiniTool.filterContent(token, userEntity.getOpenid(), 2, 2, entity.getContent(), null);
//        if (!result.isStatus()) {
//            return ;
//        }
//        processFilterContentEntity((JSONObject) result.getResult(), userId, ProcessWechatFilterTask.CONTENT_COMMENT_TYPE, 0, commentId, 0);
//    }

//    private void filterMessageContent(long userId, long messageId) {
//        BiuUserEntity userEntity = userService.find(userId);
//        BiuMessageEntity entity = biuDbFactory.getUserDbFactory().getBiuMessageImpl().find(messageId);
//        String token = String.valueOf(wechatProcessor.accessToken(miniWechatConfig).getResult());
//        FuncResult result = WechatMiniTool.filterContent(token, userEntity.getOpenid(), 1, 2, entity.getContent(), null);
//        if (!result.isStatus()) {
//            return ;
//        }
//        processFilterContentEntity((JSONObject) result.getResult(), userId, ProcessWechatFilterTask.CONTENT_MESSAGE_TYPE, 0, 0, messageId);
//    }
}
