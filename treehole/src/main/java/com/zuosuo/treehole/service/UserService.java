package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.impl.BiuHoleNoteLabelImpl;
import com.zuosuo.biudb.impl.BiuHoleNoteMoodImpl;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.cache.redis.ListOperator;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.time.TimeFormat;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.component.tool.StringTool;
import com.zuosuo.mybatis.provider.CheckStatusEnum;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.config.SystemOption;
import com.zuosuo.treehole.config.TaskOption;
import com.zuosuo.treehole.result.*;
import com.zuosuo.treehole.task.ProcessWechatFilterTask;
import com.zuosuo.treehole.task.SendUserWechatMessageTask;
import com.zuosuo.treehole.task.UserCollectInput;
import com.zuosuo.treehole.tool.AddressTool;
import com.zuosuo.treehole.tool.HashTool;
import com.zuosuo.treehole.tool.QiniuTool;
import com.zuosuo.treehole.tool.WechatTool;
import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("UserService")
public class UserService {

    @Autowired
    private QiniuTool qiniuTool;
    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private AreaService areaService;
    @Autowired
    private HashTool hashTool;
    @Autowired
    private BiuRedisFactory biuRedisFactory;
    @Autowired
    private WechatTool wechatTool;
    @Value("${biu.env}")
    private String env;

    public String createRandomNickName() {
        return "BIU笔友" + StringTool.random(4);
    }

    public BiuUserEntity getUserByOpenid(String openid) {
        ProviderOption option = new ProviderOption();
        option.addCondition("openid", openid);
        option.addCondition("use_status", BiuUserEntity.USER_AVAIL_STATUS);
        return biuDbFactory.getUserDbFactory().getBiuUserImpl().single(option);
    }

    public BiuUserEntity find(long id) {
        return biuDbFactory.getUserDbFactory().getBiuUserImpl().find(id);
    }

    // 获取用户视图
    public BiuUserViewEntity getUserView(long id) {
        return biuDbFactory.getUserDbFactory().getBiuUserViewImpl().find(id);
    }

    // 设置用户更新时间
    public void setUserSortTime(long id) {
        ProviderOption option = new ProviderOption();
        option.addCondition("id", id);
        option.addCondition("DATE_FORMAT(NOW(),'%Y-%m-%d')!=(IF(sort_time,DATE_FORMAT(sort_time,'%Y-%m-%d'),''))");
        option.setAttribute("sort_time", TimeTool.formatDate(new Date()));
        biuDbFactory.getUserDbFactory().getBiuUserImpl().modify(option);
    }

    public void initUserPenStatus(BiuUserEntity entity) {
        BiuUserViewEntity user = getUserView(entity.getId());
        int condition = 0;
        if (user.getTitle() != null && !user.getTitle().isEmpty()) {
            condition ++;
        }
        if (user.getIntroduce() != null && !user.getIntroduce().isEmpty()) {
            condition ++;
        }
        if (user.getSelfInterest() != null && !user.getSelfInterest().isEmpty()) {
            condition ++;
        }
        if (user.getSelfCommunicate() != null && !user.getSelfCommunicate().isEmpty()) {
            condition ++;
        }
        if (condition < 4) {
            entity.setIsPenuser(BiuUserEntity.USER_NOT_PEN);
        } else {
            boolean communicate_status = true;
            if (user.getSelfCommunicate().contains("1") && (user.getAddress() == null || (user.getAddress() != null && user.getAddress().isEmpty()))) {
                communicate_status = false;
            }
            if (communicate_status && user.getSelfCommunicate().contains("2") && (user.getEmail() == null || (user.getEmail() != null && user.getEmail().isEmpty()))) {
                communicate_status = false;
            }
            if (communicate_status) {
                entity.setIsPenuser(BiuUserEntity.USER_IS_PEN);
                if (user.getPenPubMsg() != 1) {
                    entity.setSearchStatus(BiuUserEntity.SEARCH_OPEN_STATUS);
                }
            } else {
                entity.setIsPenuser(BiuUserEntity.USER_NOT_PEN);
            }
        }
        entity.setPenPubMsg(1);
        biuDbFactory.getUserDbFactory().getBiuUserImpl().update(entity);
    }

    public List<String> getUserImageList(long userId, int type) {
        return getUserImageList(userId, type, 0);
    }

    public List<String> getUserImageList(long userId, int type, int limit) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        if (limit > 0) {
            option.setUsePager(true);
            option.setOffset(0);
            option.setLimit(limit);
        }
        option.addOrderby("sort_index asc");
        List<BiuUserImageEntity> list = biuDbFactory.getUserDbFactory().getBiuUserImageImpl().list(option);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().map(item -> parseImage(item.getFile())).collect(Collectors.toList());
    }

    public String parseImage(String file) {
        if (file == null) {
            return "";
        }
        if (file.trim().isEmpty()) {
            return "";
        }
        file = file.trim();
        if (!file.startsWith("upload/")) {
            return file;
        }
        return qiniuTool.getLink(file);
    }

    public List<UserInterestResult> getUserInterests(long userId) {
        List<UserInterestResult> result = new ArrayList<>();
        List<BiuUserInterestEntity> list = biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().list(new ProviderOption(new ArrayList<String>() {{
            add("user_id=" + userId);
            add("use_type=" + BiuUserInterestEntity.USE_TYPE_SELF);
        }}));
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> relates = list.stream().map(item -> String.valueOf(item.getInterestId())).collect(Collectors.toList());
        List<BiuInterestEntity> interests = biuDbFactory.getUserDbFactory().getBiuInterestImpl().list(new ProviderOption(new ArrayList<String>() {{
            add("id in (" + String.join(",", relates) + ")");
        }}));
        interests.forEach(item -> {
            result.add(new UserInterestResult(item.getId(), item.getTag(), 1, item.getTagGroup()));
        });
        return result;
    }

    public List<String> getUserInterestSimpleList(long userId) {
        List<UserInterestResult> list = getUserInterests(userId);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().map(UserInterestResult::getName).collect(Collectors.toList());
    }

    public List<UserInterestResult> getUserInterestList(long userId) {
        List<UserInterestResult> interestList = getUserInterests(userId);
        List<Long> interests = interestList.stream().map(UserInterestResult::getId).collect(Collectors.toList());
        List<BiuInterestEntity> list = biuDbFactory.getUserDbFactory().getBiuInterestImpl().list(new ProviderOption());
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserInterestResult> result = new ArrayList<>();
        list.forEach(item -> {
            result.add(new UserInterestResult(item.getId(), item.getTag(), interests.contains(item.getId()) ? 1 : 0, item.getTagGroup()));
        });
        return result;
    }

    public List<Map<String, Object>> getGroupInterestList(long userId) {
        List<UserInterestResult> interestList = getUserInterests(userId);
        List<Long> interests = interestList.stream().map(UserInterestResult::getId).collect(Collectors.toList());
        List<BiuInterestEntity> list = biuDbFactory.getUserDbFactory().getBiuInterestImpl().list(new ProviderOption());
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> result = new ArrayList<>();
        list.forEach(item -> {
            Map<String, Object> unit = new HashMap<>();
            unit.put("id", item.getId());
            unit.put("checked", interests.contains(item.getId()) ? 1 : 0);
            unit.put("name", item.getTag());
            unit.put("tag_group", item.getTagGroup());
            result.add(unit);
        });
        return result;
    }

    public BiuUserImageEntity getHashImage(long userId, String hash) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("hash_code", hash);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public BiuUserImageEntity initEmptyHashImage(long userId, String file, String hash) {
        BiuUserImageEntity image = new BiuUserImageEntity();
        image.setUserId(userId);
        image.setFile(file);
        image.setHashCode(hash);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().insert(image);
    }

    public long clearUserImage(long userId, int type, long relateId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        option.addCondition("relate_id", relateId);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().destroy(option);
    }

    public BiuUserImageEntity getUserImage(long userId, String file) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("file", file);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public BiuUserImageEntity getUserImage(long userId, int type, long relateId, String file, CheckStatusEnum status) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        option.addCondition("relate_id", relateId);
        option.addCondition("file", file);
        option.setStatus(status.getValue());
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public void setUserImage(long userId, int type, long relateId, List<String> files) {
        clearUserImage(userId, type, relateId);
        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                setUserImage(userId, type, relateId, files.get(i), i + 1);
            }
        }
    }

    public BiuUserImageEntity setUserImage(long userId, int type, long relateId, String file, int sort) {
        boolean isQiniu = false;
        String originUrl = file;
        if (file == null) {
            return null;
        }
        if (file.isEmpty()) {
            return null;
        }
        if (file.contains("wxfile://")) {
            return null;
        }
        if (file.contains("/upload")) {
            file = file.substring(file.indexOf("/upload") + 1);
            isQiniu = true;
        }
        BiuUserImageEntity image = getUserImage(userId, type, relateId, file, CheckStatusEnum.DELETED);
        if (image == null) {
            image = new BiuUserImageEntity();
            image.setUserId(userId);
            image.setUseType(type);
            image.setRelateId(relateId);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().insert(image);
        } else {
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().restore(image);
        }
        if (isQiniu) {
            BiuUserImageEntity relate = getUserImage(userId, file);
            if (relate == null) {
                relate = getUserImage(0, file);
            }
            if (relate != null) {
                image.setFile(relate.getFile());
                image.setUseType(type);
                image.setHashCode(relate.getHashCode());
                image.setSortIndex(sort);
                biuDbFactory.getUserDbFactory().getBiuUserImageImpl().update(image);
                filterByWechat(ProcessWechatFilterTask.FILTER_MEDIA, userId, ProcessWechatFilterTask.MEDIA_IMAGE_TYPE, image.getId(), type, relateId);
                return image;
            } else {
                file = originUrl;
            }
        }
        image.setFile(file);
        image.setUseType(type);
        image.setHashCode("");
        image.setSortIndex(sort);
        biuDbFactory.getUserDbFactory().getBiuUserImageImpl().update(image);
        filterByWechat(ProcessWechatFilterTask.FILTER_MEDIA, userId, ProcessWechatFilterTask.MEDIA_IMAGE_TYPE, image.getId(), type, relateId);
        return image;
    }

    public long clearUserCommunicate(long userId, int type) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        return biuDbFactory.getUserDbFactory().getBiuUserCommunicateImpl().destroy(option);
    }

    public void setUserCommunicate(long userId, int type, List<Integer> communicates) {
        clearUserCommunicate(userId, type);
        if (!communicates.isEmpty()) {
            for (int i = 0; i < communicates.size(); i++) {
                setUserCommunicate(userId, type, communicates.get(i));
            }
        }
    }

    public void setUserCommunicate(long userId, int type, int communicate) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        option.addCondition("com_method", communicate);
        option.setStatus(CheckStatusEnum.ALL.getValue());
        BiuUserCommunicateEntity entity = biuDbFactory.getUserDbFactory().getBiuUserCommunicateImpl().single(option);
        if (entity == null) {
            entity = new BiuUserCommunicateEntity();
            entity.setUserId(userId);
            entity.setUseType(type);
            entity.setComMethod(communicate);
            biuDbFactory.getUserDbFactory().getBiuUserCommunicateImpl().insert(entity);
        } else if (entity.getDeletedAt() != null) {
            biuDbFactory.getUserDbFactory().getBiuUserCommunicateImpl().restore(entity);
        }
    }

    public long clearUserSex(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        return biuDbFactory.getUserDbFactory().getBiuUserSexImpl().destroy(option);
    }

    public void setUserSexes(long userId, List<Integer> sexes) {
        clearUserSex(userId);
        if (!sexes.isEmpty()) {
            for (int i = 0; i < sexes.size(); i++) {
                setUserSex(userId, sexes.get(i));
            }
        }
    }

    public void setUserSex(long userId, int sex) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("sex", sex);
        option.setStatus(CheckStatusEnum.ALL.getValue());
        BiuUserSexEntity entity = biuDbFactory.getUserDbFactory().getBiuUserSexImpl().single(option);
        if (entity == null) {
            entity = new BiuUserSexEntity();
            entity.setUserId(userId);
            entity.setSex(sex);
            biuDbFactory.getUserDbFactory().getBiuUserSexImpl().insert(entity);
        } else if (entity.getDeletedAt() != null) {
            biuDbFactory.getUserDbFactory().getBiuUserSexImpl().restore(entity);
        }
    }

    public long clearUserInterest(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", BiuUserInterestEntity.USE_TYPE_SELF);
        return biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().destroy(option);
    }

    public void setUserInterest(long userId, List<Long> interests) {
        clearUserInterest(userId);
        if (!interests.isEmpty()) {
            for (int i = 0; i < interests.size(); i++) {
                setUserInterest(userId, interests.get(i));
            }
        }
    }

    public void setUserInterest(long userId, long interest) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", BiuUserInterestEntity.USE_TYPE_SELF);
        option.addCondition("interest_id", interest);
        option.setStatus(CheckStatusEnum.ALL.getValue());
        BiuUserInterestEntity entity = biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().single(option);
        if (entity == null) {
            entity = new BiuUserInterestEntity();
            entity.setUserId(userId);
            entity.setUseType(BiuUserInterestEntity.USE_TYPE_SELF);
            entity.setInterestId(interest);
            biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().insert(entity);
        } else if (entity.getDeletedAt() != null) {
            biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().restore(entity);
        }
    }

    public String parseSexes(List<Integer> list) {
        List<String> sexes = list.stream().map(item -> {
            String sex;
            if (item == BiuUserEntity.USER_SEX_MAN) {
                sex = BiuUserEntity.LABEL_USER_SEX_MAN;
            } else if (item == BiuUserEntity.USER_SEX_WOMEN) {
                sex = BiuUserEntity.LABEL_USER_SEX_WOMEN;
            } else {
                sex = "";
            }
            return sex;
        }).collect(Collectors.toList());
        if (sexes.size() == 2) {
            return "不限";
        }
        return String.join("/", sexes);
    }

    public String parseCommunicates(List<Integer> list) {
        List<String> communicates = list.stream().map(item -> {
            String name;
            if (item == BiuUserCommunicateEntity.COM_METHOD_EMAIL) {
                name = BiuUserCommunicateEntity.LABEL_COM_METHOD_EMAIL;
            } else if (item == BiuUserCommunicateEntity.COM_METHOD_LETTER) {
                name = BiuUserCommunicateEntity.LABEL_COM_METHOD_LETTER;
            } else {
                name = "";
            }
            return name;
        }).collect(Collectors.toList());
        if (communicates.size() == 2) {
            return "不限";
        }
        return String.join("/", communicates);
    }

    public String parseInterests(List<UserInterestResult> list) {
        List<String> interests = list.stream().filter(item -> item.getChecked() > 0).map(UserInterestResult::getName).collect(Collectors.toList());
        return String.join("/", interests);
    }

    public BiuUserBlacklistEntity getUserBlack(long userId, long relateId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("black_id", relateId);
        return biuDbFactory.getUserDbFactory().getBiuUserBlacklistImpl().single(option);
    }

    /**
     * 转化好友列表users
     *
     * @param userId
     * @param friendId
     * @return
     */
    public String formatUserFriendMembers(long userId, long friendId) {
        List<Long> users = Arrays.asList(userId, friendId);
        Collections.sort(users);
        return String.join(",", users.stream().map(item -> String.valueOf(item)).collect(Collectors.toList()));
    }

    public BiuUserFriendEntity getUserFriend(long userId, long friendId, int confirmStatus) {
        String users = formatUserFriendMembers(userId, friendId);
        ProviderOption option = new ProviderOption();
        option.addCondition("users", users);
        option.addCondition("confirm_status", confirmStatus);
        return biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().single(option);
    }

    public BiuUserFriendEntity getUserFriend(long id) {
        return biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().find(id);
    }

    public BiuUserFriendEntity getUserFriend(long userId, long friendId) {
        String users = formatUserFriendMembers(userId, friendId);
        ProviderOption option = new ProviderOption();
        option.addCondition("users", users);
//        option.addCondition("confirm_status<>2");
        return biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().single(option);
    }

    public BiuUserFriendEntity getUserFriendCanceled(long userId, long friendId) {
        String users = formatUserFriendMembers(userId, friendId);
        ProviderOption option = new ProviderOption();
        option.addCondition("users", users);
        option.setStatus(CheckStatusEnum.DELETED.getValue());
//        option.addCondition("confirm_status<>2");
        return biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().single(option);
    }

    /**
     * 生成用户好友记录
     * @param userId
     * @param friendId
     * @param communicate
     * @return
     */
    public BiuUserFriendEntity applyFriend(long userId, long friendId, int communicate) {
        BiuUserFriendEntity passed = getUserFriend(userId, friendId, BiuUserFriendEntity.PASS_STATUS);
        if (passed != null) {
            return passed;
        }
        BiuUserFriendEntity waiting = getUserFriend(userId, friendId, BiuUserFriendEntity.WAITING_STATUS);
        if (waiting != null) {
            return waiting;
        }
        BiuUserFriendEntity friend = new BiuUserFriendEntity();
        friend.setUsers(formatUserFriendMembers(userId, friendId));
        friend.setCommunicateType(communicate);
        friend.setConfirmStatus(BiuUserFriendEntity.WAITING_STATUS);
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().insert(friend);
        createFriendMember(userId, friendId, friend);
        sendMiniMessage(SendUserWechatMessageTask.APPLY_FRIEND_TYPE, friendId, userId, friend.getId());
        return friend;
    }

    /**
     * 取消好友申请
     * @param userId
     * @param friendId
     * @return
     */
    public int rollFriend(long userId, long friendId) {
        BiuUserFriendEntity waiting = getUserFriend(userId, friendId, BiuUserFriendEntity.WAITING_STATUS);
        if (waiting == null) {
            return 0;
        }
        long sourceUserId = getFriendSource(waiting);
        if (userId != sourceUserId) {
            return -1;
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("friend_id", waiting.getId());
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().destroy(option);
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().delete(waiting);
        return 1;
    }

    public void createFriendMember(long userId, long friendId, BiuUserFriendEntity friend) {
        BiuUserViewEntity user = getUserView(userId);
        BiuUserViewEntity friendUser = getUserView(friendId);
        BiuUserFriendMemberEntity memberUser = new BiuUserFriendMemberEntity();
        memberUser.setFriendId(friend.getId());
        memberUser.setUserId(userId);
        memberUser.setConfirmStatus(BiuUserFriendMemberEntity.PASS_STATUS);
        biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().insert(memberUser);
        BiuUserFriendMemberEntity memberFriend = new BiuUserFriendMemberEntity();
        memberFriend.setFriendId(friend.getId());
        memberFriend.setUserId(friendId);
        memberFriend.setConfirmStatus(BiuUserFriendMemberEntity.WAITING_STATUS);
        biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().insert(memberFriend);
        addUserMessage(userId, userId, BiuMessageEntity.NOTICE_APPLY, friendId, friend.getId(), SystemOption.APPLY_TITLE.getValue().replace("#NAME#", friendUser.getPenName()), "");
        addUserMessage(userId, friendId, BiuMessageEntity.NOTICE_APPLY, userId, friend.getId(), SystemOption.RECEIVE_APPLY_TITLE.getValue().replace("#NAME#", user.getPenName()), "");
    }

    public void addUserMessage(long sourceId, long destId, int messageType, long relateId, long friendId, String title, String content) {
        BiuMessageEntity message = new BiuMessageEntity();
        message.setSourceId(sourceId);
        message.setDestId(destId);
        message.setMessageType(messageType);
        message.setRelateId(relateId);
        message.setFriendId(friendId);
        message.setTitle(title);
        message.setContent(content);
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().insert(message);
    }

    public void addUserMessage(long sourceId, long destId, int messageType, long relateId, String title, String content) {
        BiuMessageEntity message = new BiuMessageEntity();
        message.setSourceId(sourceId);
        message.setDestId(destId);
        message.setMessageType(messageType);
        message.setRelateId(relateId);
        message.setTitle(title);
        message.setContent(content);
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().insert(message);
    }

    public void addUserMessage(long sourceId, long destId, int messageType, long relateId, String title, String content, String banner, List<String> images) {
        BiuMessageEntity message = new BiuMessageEntity();
        message.setSourceId(sourceId);
        message.setDestId(destId);
        message.setMessageType(messageType);
        message.setRelateId(relateId);
        message.setTitle(title);
        message.setBanner(banner);
        message.setContent(content);
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().insert(message);
        setUserImage(0, BiuUserImageEntity.USE_TYPE_MESSAGE, message.getId(), images);
    }

    /**
     * 私信消息发送
     * @param sourceId
     * @param destId
     * @param messageType
     * @param relateId
     * @param friendId
     * @param isFriend
     * @param contentType
     * @param title
     * @param content
     * @param banner
     * @param images
     */
    public void addUserMessage(long sourceId, long destId, int messageType, long relateId, long friendId, int isFriend, String contentType, String title, String content, String banner, List<String> images) {
        String users = formatUserFriendMembers(sourceId, destId);
        BiuMessageEntity message = new BiuMessageEntity();
        message.setSourceId(sourceId);
        message.setDestId(destId);
        message.setMessageType(messageType);
        message.setRelateId(relateId);
        message.setFriendId(friendId);
        message.setContentType(contentType);
        message.setUsers(users);
        message.setTitle(title);
        message.setBanner(banner);
        message.setContent(content);
        message.setIsFriend(isFriend);
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().insert(message);
        setUserImage(0, BiuUserImageEntity.USE_TYPE_MESSAGE, message.getId(), images);
        addUserFriendMessageRelate(message.getId(), friendId, sourceId, users, 1);
        addUserFriendMessageRelate(message.getId(), friendId, destId, users);
        sendMiniMessage(SendUserWechatMessageTask.PRIVATE_MESSAGE_TYPE, destId, sourceId, message.getId());
//        filterByWechat(ProcessWechatFilterTask.FILTER_CONTENT, sourceId, ProcessWechatFilterTask.CONTENT_MESSAGE_TYPE, message.getId());
    }

    /**
     * 私信用户消息关联
     * @param messageId
     * @param friendId
     * @param userId
     * @param users
     */
    public void addUserFriendMessageRelate(long messageId, long friendId, long userId, String users, int readStatus) {
        BiuUserFriendMessageEntity relate = new BiuUserFriendMessageEntity();
        relate.setMessageId(messageId);
        relate.setFriendId(friendId);
        relate.setUserId(userId);
        relate.setReadStatus(readStatus);
        relate.setUsers(users);
        biuDbFactory.getUserDbFactory().getBiuUserFriendMessageImpl().insert(relate);
    }

    public void addUserFriendMessageRelate(long messageId, long friendId, long userId, String users) {
        addUserFriendMessageRelate(messageId, friendId, userId, users, 0);
    }


    public BiuUserFriendMemberEntity getUserFriendMember(long friendId, long userId, int status) {
        ProviderOption option = new ProviderOption();
        option.addCondition("friend_id", friendId);
        option.addCondition("user_id", userId);
        option.addCondition("confirm_status", status);
        return biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().single(option);
    }

    /**
     * 通过好友申请
     * @param userId
     * @param friendId
     * @return
     */
    public JsonResult passFriend(long userId, long friendId) {
        BiuUserViewEntity authUser = getUserView(userId);
        BiuUserFriendEntity passed = getUserFriend(userId, friendId, BiuUserFriendEntity.PASS_STATUS);
        if (passed != null) {
            return new JsonResult("已通过申请");
        }
        BiuUserFriendEntity waiting = getUserFriend(userId, friendId, BiuUserFriendEntity.WAITING_STATUS);
        if (waiting == null) {
            return new JsonResult("非有效好友申请");
        }
        BiuUserFriendMemberEntity member = getUserFriendMember(waiting.getId(), userId, BiuUserFriendMemberEntity.WAITING_STATUS);
        if (member == null) {
            return new JsonResult("无有效好友申请审核");
        }
        member.setConfirmStatus(BiuUserFriendMemberEntity.PASS_STATUS);
        member.setConfirmTime(new Date());
        biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().update(member);
        waiting.setConfirmStatus(BiuUserFriendEntity.PASS_STATUS);
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().update(waiting);
        addUserMessage(userId, friendId, BiuMessageEntity.NOTICE_FRIEND, userId, waiting.getId(), SystemOption.FRIEND_PASS_TITLE.getValue().replace("#NAME#", authUser.getPenName()), "");
        // 陌生私信转好友私信
        String users = formatUserFriendMembers(userId, friendId);
        ProviderOption option = new ProviderOption();
        option.addCondition("message_type", BiuMessageEntity.PRIVATE_MESSAGE);
        option.addCondition("users", users);
        option.setAttribute("is_friend", 1);
        option.setAttribute("friend_id", waiting.getId());
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().modify(option);
        // 私信关联更新
        option = new ProviderOption();
        option.addCondition("users", users);
        option.setAttribute("friend_id", waiting.getId());
        biuDbFactory.getUserDbFactory().getBiuUserFriendMessageImpl().modify(option);
        return JsonResult.success();
    }

    public JsonResult refuseFriend(long userId, long friendId) {
        BiuUserViewEntity authUser = getUserView(userId);
        BiuUserFriendEntity refuseed = getUserFriend(userId, friendId, BiuUserFriendEntity.REFUSE_STATUS);
        if (refuseed != null) {
            return new JsonResult("已拒绝申请");
        }
        BiuUserFriendEntity waiting = getUserFriend(userId, friendId, BiuUserFriendEntity.WAITING_STATUS);
        if (waiting == null) {
            return new JsonResult("非有效好友申请");
        }
        BiuUserFriendMemberEntity member = getUserFriendMember(waiting.getId(), userId, BiuUserFriendMemberEntity.WAITING_STATUS);
        if (member == null) {
            return new JsonResult("无有效好友申请审核");
        }
        member.setConfirmStatus(BiuUserFriendMemberEntity.REFUSE_STATUS);
        member.setConfirmTime(new Date());
        biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().update(member);
        waiting.setConfirmStatus(BiuUserFriendEntity.REFUSE_STATUS);
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().update(waiting);
        addUserMessage(userId, friendId, BiuMessageEntity.NOTICE_FRIEND, userId, waiting.getId(), SystemOption.FRIEND_REFUSE_TITLE.getValue().replace("#NAME#", authUser.getPenName()), "");
        return JsonResult.success();
    }

    public JsonResult removeFriend(long userId, long friendId) {
        BiuUserFriendEntity passed = getUserFriend(userId, friendId, BiuUserFriendEntity.PASS_STATUS);
        if (passed == null) {
            BiuUserFriendEntity waiting = getUserFriend(userId, friendId, BiuUserFriendEntity.WAITING_STATUS);
            if (waiting == null) {
                return new JsonResult("非好友关系");
            } else {
                biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().delete(waiting);
            }
        } else {
            biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().delete(passed);
        }
        removeFriendMessage(friendId);
        return JsonResult.success();
    }

    public void removeUserFriend(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.setColumns("friend_id");
        List<BiuUserFriendMemberEntity> members = biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().list(option);
        List<Long> friendList = members.stream().map(item -> item.getFriendId()).distinct().collect(Collectors.toList());
        friendList.forEach(item -> removeFriendMessage(item));
        option = new ProviderOption();
        option.addCondition("id in (" + friendList.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().destroy(option);
    }

    public void removeFriendMessage(long friendId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("friend_id", friendId);
        option.addCondition("message_type in (" + Arrays.asList(BiuMessageEntity.NOTICE_APPLY, BiuMessageEntity.NOTICE_FRIEND, BiuMessageEntity.PRIVATE_MESSAGE).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().destroy(option);
        option = new ProviderOption();
        option.addCondition("friend_id", friendId);
        biuDbFactory.getUserDbFactory().getBiuUserFriendMessageImpl().destroy(option);
        option = new ProviderOption();
        option.addCondition("friend_id", friendId);
        option.setColumns("id");
        List<BiuUserFriendCommunicateLogEntity> logs = biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().list(option);
        if (!logs.isEmpty()) {
            option = new ProviderOption();
            option.addCondition("message_type in (" + Arrays.asList(BiuMessageEntity.NOTICE_SEND, BiuMessageEntity.NOTICE_RECEIVE).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
            option.addCondition("relate_id in (" + logs.stream().map(item -> String.valueOf(item.getId())).collect(Collectors.joining(",")) + ")");
            biuDbFactory.getUserDbFactory().getBiuMessageImpl().destroy(option);
        }
    }

    public void removeFriendPrivateMessage(long userId) {
        ProviderOption option = new ProviderOption();
        option.setColumns("id");
        option.addCondition("message_type", BiuMessageEntity.PRIVATE_MESSAGE);
        String condition = "(dest_id=#DEST# or source_id=#SOURCE#)";
        option.addCondition(condition.replace("#DEST#", String.valueOf(userId)).replace("#SOURCE#", String.valueOf(userId)));
        List<BiuMessageEntity> list = biuDbFactory.getUserDbFactory().getBiuMessageImpl().list(option);
        String messageIds = list.stream().map(item -> String.valueOf(item.getId())).collect(Collectors.joining(","));
        option = new ProviderOption();
        option.addCondition("id in (" + messageIds + ")");
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().destroy(option);
        option = new ProviderOption();
        option.addCondition("message_id in (" + messageIds + ")");
        biuDbFactory.getUserDbFactory().getBiuUserFriendMessageImpl().destroy(option);
    }

    public void removeFriendPrivateMessageById(long messageId) {
        ProviderOption option = new ProviderOption();
        option.setColumns("id");
        option.addCondition("message_id", messageId);
        List<BiuUserFriendMessageEntity> list = biuDbFactory.getUserDbFactory().getBiuUserFriendMessageImpl().list(option);
        if (list.size() == 0) {
            option = new ProviderOption();
            option.addCondition("id", messageId);
            biuDbFactory.getUserDbFactory().getBiuMessageImpl().destroy(option);
        }
    }

    public long getFriendId(BiuUserFriendEntity friend, long userId) {
        if (friend == null) {
            return 0;
        }
        List<Long> users = Arrays.asList(friend.getUsers().split(",")).stream().map(item -> Long.parseLong(item)).collect(Collectors.toList());
        long friendId = 0;
        for (long memberId: users) {
            if (memberId != userId) {
                friendId = memberId;
            }
        }
        return friendId;
    }

    /**
     * 获取好友关系发起人
     * @param friend
     * @return
     */
    public long getFriendSource(BiuUserFriendEntity friend) {
        if (friend == null) {
            return 0;
        }
        if (friend.getConfirmStatus() != BiuUserFriendMemberEntity.WAITING_STATUS) {
            return 0;
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("confirm_status", BiuUserFriendMemberEntity.PASS_STATUS);
        option.addCondition("friend_id", friend.getId());
        BiuUserFriendMemberEntity sourceUser = biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().single(option);
        if (sourceUser == null) {
            return 0;
        }
        return sourceUser.getUserId();
    }

    public boolean allowAuthFriend(BiuUserFriendEntity friend, long userId) {
        if (friend == null) {
            return false;
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("friend_id", friend.getId());
        option.addCondition("user_id", userId);
        option.addCondition("confirm_status", BiuUserFriendMemberEntity.WAITING_STATUS);
        BiuUserFriendMemberEntity entity = biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().single(option);
        if (entity != null) {
            return true;
        }
        return false;
    }

    public String getUserAddress(long userId) {
        BiuUserViewEntity user = getUserView(userId);
        List<String> address = new ArrayList<>();
//        String province = areaService.getArea(user.getProvince());
//        if (!province.isEmpty()) {
//            address.add(province);
//        }
//        String city = areaService.getArea(user.getCity());
//        if (!city.isEmpty()) {
//            address.add(city);
//        }
//        String country = areaService.getArea(user.getCountry());
//        if (!country.isEmpty()) {
//            address.add(country);
//        }
        if (!user.getAddress().isEmpty()) {
            address.add(user.getAddress());
        }
        if (!user.getStreet().isEmpty()) {
            address.add(user.getStreet());
        }
        return String.join("", address);
    }

    public String encodeHash(long number) {
        return hashTool.getHashids(4).encode(number);
    }

    public long decodeHash(String hash) {
        return hashTool.getHashids(4).first(hash);
    }

    public List<FriendCommunicateInfo> getFriendCommunicateList(List<Long> users) {
        List<FriendCommunicateInfo> communicates = new ArrayList<>();
        BiuUserViewEntity user = getUserView(users.get(0));
        BiuUserViewEntity friendUser = getUserView(users.get(1));
        Arrays.asList(BiuUserCommunicateEntity.COM_METHOD_LETTER, BiuUserCommunicateEntity.COM_METHOD_EMAIL).stream().filter(item -> {
            String compareValue = "'" + item + "'";
            if (user.getSelfCommunicate().contains(compareValue) && friendUser.getSelfCommunicate().contains(compareValue)) {
                return true;
            }
            return false;
        }).forEach(item -> {
            if (item == BiuUserCommunicateEntity.COM_METHOD_LETTER) {
                communicates.add(new FriendCommunicateInfo(item, BiuUserCommunicateEntity.LABEL_COM_METHOD_LETTER));
            } else if(item == BiuUserCommunicateEntity.COM_METHOD_EMAIL) {
                communicates.add(new FriendCommunicateInfo(item, BiuUserCommunicateEntity.LABEL_COM_METHOD_EMAIL));
            }
        });
        return communicates;
    }

    public void initFriendCommunicate(BiuUserFriendEntity friend, BiuUserEntity member, UserFriendCommunicateInfo info) {
        if (friend.getCommunicateType() == BiuUserCommunicateEntity.COM_METHOD_LETTER) {
            info.getInfo().put("name", member.getUsername());
            info.getInfo().put("phone", member.getPhone());
            info.getInfo().put("address", getUserAddress(member.getId()));
            info.getInfo().put("zipcode", member.getZipcode());
        } else if(friend.getCommunicateType() == BiuUserCommunicateEntity.COM_METHOD_EMAIL) {
            info.getInfo().put("email", member.getEmail());
        }
    }

    public void initFriendCommunicate(BiuUserFriendEntity friend, BiuUserViewEntity member, UserFriendCommunicateInfo info) {
        if (friend.getCommunicateType() == BiuUserCommunicateEntity.COM_METHOD_LETTER) {
            info.getInfo().put("name", member.getUsername());
            info.getInfo().put("phone", member.getPhone());
            info.getInfo().put("address", getUserAddress(member.getId()));
            info.getInfo().put("zipcode", member.getZipcode());
        } else if(friend.getCommunicateType() == BiuUserCommunicateEntity.COM_METHOD_EMAIL) {
            info.getInfo().put("email", member.getEmail());
        }
    }

    public List<UserFriendResult> processFriendList(List<BiuUserFriendEntity> friends, long userId) {
        List<UserFriendResult> list = new ArrayList<>();
        friends.forEach(friend -> {
            UserFriendResult unit = new UserFriendResult();
            unit.setId(encodeHash(friend.getId()));
            unit.getCommunicateInfo().setCommunicate(friend.getCommunicateType());
            long friendId = getFriendId(friend, userId);
            unit.setFriend(encodeHash(friendId));
            BiuUserEntity friendUser = biuDbFactory.getUserDbFactory().getBiuUserImpl().find(friendId);
            unit.setPriMsgStatus(friendUser != null ? friendUser.getPriMsgStatus() : 0);
            BiuUserViewEntity member = getUserView(friendId);
            if (member != null && member.getUseStatus() == BiuUserEntity.USER_AVAIL_STATUS) {
                unit.setName(member.getPenName());
                unit.setImage(parseImage(member.getImage()));
                unit.setDesc(TimeTool.friendlyTime(member.getSortTime(), "yyyy/MM/dd"));
//                unit.setDesc(getUserDesc(member));
                initFriendCommunicate(friend, member, unit.getCommunicateInfo());
                if (friend.getLastLog() > 0) {
                    BiuUserFriendCommunicateLogEntity log = biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().find(friend.getLastLog());
                    if (log.getReceiveStatus() == 0 && log.getReceiveUser() == userId) {
                        unit.getCommunicateInfo().setAllowReceive(1);
                    }
                    unit.getCommunicateInfo().setLogId(encodeHash(log.getId()));
                    unit.getCommunicateInfo().setReceived(log.getReceiveStatus());
                    unit.getCommunicateInfo().setLabel(log.getReceiveStatus() == 0? (log.getSendUser() == userId ? "邮件已寄出" : "笔友已寄出邮件"): (log.getReceiveUser() == userId ? "邮件已接收" : "笔友已接收邮件"));
                    unit.getCommunicateInfo().setLogTime(TimeTool.formatDate(log.getCreatedAt(), "yyyy/MM/dd"));
                }
                unit.getCommunicateInfo().setSendTag("邮件已寄出");
                unit.getCommunicateInfo().setReceiveTag("邮件已接收");
                list.add(unit);
            }
        });
        return list;
    }

    public List<Map> processFriendCommunicateLogs(BiuUserFriendEntity friend, List<BiuUserFriendCommunicateLogEntity> logs, long userId) {
        List<Map> list = new ArrayList<>();
        logs.forEach(item -> {
            Map<String, Object> unit = new HashMap<>();
            unit.put("logId", encodeHash(item.getId()));
            unit.put("friendId", encodeHash(friend.getId()));
            unit.put("label", item.getReceiveStatus() == 0? (item.getSendUser() == userId ? "邮件已寄出" : "笔友已寄出邮件"): (item.getReceiveUser() == userId ? "邮件已接收" : "笔友已接收邮件"));
            if (item.getReceiveStatus() == 0 && item.getReceiveUser() == userId) {
                unit.put("allowReceive", 1);
            } else {
                unit.put("allowReceive", 0);
            }
            unit.put("communicate", item.getCommunicateType());
            unit.put("received", item.getReceiveStatus());
            unit.put("sendTag", "邮件已寄出");
            unit.put("receiveTag", "邮件已接收");
            unit.put("logTime", TimeTool.formatDate(item.getCreatedAt(), "yyyy/MM/dd"));
            list.add(unit);
        });
        return list;
    }

    public Map sendFriendCommunicate(BiuUserFriendEntity friend, long sendUser) {
        Map<String, Object> result = new HashMap<>();
        long receiveUser = getFriendId(friend, sendUser);
        BiuUserFriendCommunicateLogEntity log = new BiuUserFriendCommunicateLogEntity();
        log.setFriendId(friend.getId());
        log.setCommunicateType(friend.getCommunicateType());
        log.setSendUser(sendUser);
        log.setReceiveUser(receiveUser);
        biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().insert(log);
        friend.setLastLog(log.getId());
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().update(friend);
        BiuUserViewEntity sender = getUserView(sendUser);
        addUserMessage(sendUser, receiveUser, BiuMessageEntity.NOTICE_SEND, log.getId(), SystemOption.SEND_MAIL_TITLE.getValue().replace("#NAME#", sender.getPenName()), "");
        sendMiniMessage(SendUserWechatMessageTask.LETTER_REPLY_TYPE, receiveUser, sendUser, log.getId());
        result.put("allow_receive", 0);
        result.put("label", "邮件已寄出");
        result.put("log", encodeHash(log.getId()));
        result.put("time", TimeTool.formatDate(new Date(), "yyyy/MM/dd"));
        return result;
    }

    public Map receiveFriendCommunicate(BiuUserFriendEntity friend, long receiveUser) {
        Map<String, Object> result = new HashMap<>();
        long sendUser = getFriendId(friend, receiveUser);
        BiuUserFriendCommunicateLogEntity log = new BiuUserFriendCommunicateLogEntity();
        log.setFriendId(friend.getId());
        log.setCommunicateType(friend.getCommunicateType());
        log.setSendUser(sendUser);
        log.setReceiveUser(receiveUser);
        log.setReceiveStatus(BiuUserFriendCommunicateLogEntity.RECEIVED);
        log.setReceiveTime(new Date());
        biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().insert(log);
        friend.setLastLog(log.getId());
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().update(friend);
        BiuUserViewEntity receiver = getUserView(receiveUser);
        addUserMessage(log.getReceiveUser(), log.getSendUser(), BiuMessageEntity.NOTICE_RECEIVE, log.getId(), SystemOption.RECEIVE_MAIL_TITLE.getValue().replace("#NAME#", receiver.getPenName()), "");
        result.put("allow_receive", 0);
        result.put("label", "邮件已接收");
        result.put("log", encodeHash(log.getId()));
        result.put("time", TimeTool.formatDate(new Date(), "yyyy/MM/dd"));
        return result;
    }

    public Map receiveFriendCommunicate(BiuUserFriendCommunicateLogEntity log) {
        Map<String, Object> result = new HashMap<>();
        log.setReceiveStatus(BiuUserFriendCommunicateLogEntity.RECEIVED);
        log.setReceiveTime(new Date());
        biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().update(log);
        BiuUserViewEntity receiver = getUserView(log.getReceiveUser());
        addUserMessage(log.getReceiveUser(), log.getSendUser(), BiuMessageEntity.NOTICE_RECEIVE, log.getId(), SystemOption.RECEIVE_MAIL_TITLE.getValue().replace("#NAME#", receiver.getPenName()), "");
        result.put("allow_receive", 0);
        result.put("label", "笔友已收到邮件");
        result.put("log", encodeHash(log.getId()));
        result.put("time", TimeTool.formatDate(log.getCreatedAt(), "yyyy/MM/dd"));
        return result;
    }

    public String getUserDesc(BiuUserViewEntity user) {
        List<String> descList = new ArrayList<>();
        List<String> areas = new ArrayList<>();
        String nation = areaService.getArea(user.getNation());
        if (!nation.isEmpty()) {
            areas.add(nation);
        }
        String province = areaService.getArea(user.getProvince());
        if (!province.isEmpty()) {
            areas.add(province);
        }
        if (!areas.isEmpty()) {
            descList.add(areas.stream().collect(Collectors.joining("-")));
        }
        String sex = user.getSexTag();
        if (!sex.isEmpty()) {
            descList.add(sex);
        }
        int age = user.getAge();
        if (age > 0) {
            descList.add(age + "岁");
        }
        return String.join("/", descList);
    }

    public List<NoteLabelResult> getUserLabelList(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addOrderby("use_time desc");
        option.setUsePager(true);
        option.setLimit(10);
        return getNoteLabelList(option);
    }

    public List<NoteLabelResult> getRecommendLabelList() {
        ProviderOption option = new ProviderOption();
        option.addCondition("is_recommend", 1);
        option.addOrderby("use_time desc");
        option.setUsePager(true);
        option.setLimit(10);
        return getNoteLabelList(option);
    }

    public List<NoteLabelResult> getNoteLabelList(ProviderOption option) {
        List<BiuLabelEntity> list = biuDbFactory.getHoleDbFactory().getLabelImpl().list(option);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<NoteLabelResult> result = new ArrayList<>();
        list.forEach(item -> {
            NoteLabelResult unit = new NoteLabelResult();
            unit.setId(item.getId());
            unit.setTag(item.getTag());
            result.add(unit);
        });
        return result;
    }

    public List<NoteMoodResult> getNoteMoodList() {
        ProviderOption option = new ProviderOption();
        option.setUsePager(true);
        option.setLimit(10);
        List<BiuMoodEntity> list = biuDbFactory.getHoleDbFactory().getMoodImpl().list(option);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<NoteMoodResult> result = new ArrayList<>();
        list.forEach(item -> {
            NoteMoodResult unit = new NoteMoodResult();
            unit.setCode(item.getCode());
            unit.setTag(item.getTag());
            unit.setEmoj(item.getEmoj());
            result.add(unit);
        });
        return result;
    }

    public Map getNoteInitSelection(long userId) {
        Map<String, List> selections = new HashMap<>();
        selections.put("my_label", getUserLabelList(userId));
        selections.put("recommend_label", getRecommendLabelList());
        selections.put("moods", getNoteMoodList());
        return selections;
    }

    public void setNoteLabel(long userId, long noteId, long label) {
        BiuHoleNoteLabelImpl biuHoleNoteLabelImpl = biuDbFactory.getHoleDbFactory().getBiuHoleNoteLabelImpl();
        ProviderOption option = new ProviderOption();
        option.addCondition("note_id", noteId);
        biuHoleNoteLabelImpl.destroy(option);
        option = new ProviderOption();
        option.addCondition("note_id", noteId);
        option.addCondition("label_id", label);
        option.setStatus(CheckStatusEnum.DELETED.getValue());
        BiuHoleNoteLabelEntity entity = biuHoleNoteLabelImpl.single(option);
        if (entity == null) {
            entity.setUserId(userId);
            entity.setNoteId(noteId);
            entity.setLabelId(label);
            biuHoleNoteLabelImpl.insert(entity);
        } else {
            biuHoleNoteLabelImpl.restore(entity);
        }
        option = new ProviderOption();
        option.addCondition("id", label);
        option.setAttribute("use_time", "NOW()", true);
        biuDbFactory.getHoleDbFactory().getLabelImpl().modify(option);
    }

    public void setNoteMood(long userId, long noteId, long mood) {
        BiuHoleNoteMoodImpl biuHoleNoteMoodImpl = biuDbFactory.getHoleDbFactory().getBiuHoleNoteMoodImpl();
        ProviderOption option = new ProviderOption();
        option.addCondition("note_id", noteId);
        biuHoleNoteMoodImpl.destroy(option);
        option = new ProviderOption();
        option.addCondition("note_id", noteId);
        option.addCondition("mood_id", mood);
        option.setStatus(CheckStatusEnum.DELETED.getValue());
        BiuHoleNoteMoodEntity entity = biuHoleNoteMoodImpl.single(option);
        if (entity == null) {
            entity.setUserId(userId);
            entity.setNoteId(noteId);
            entity.setMoodId(mood);
            biuHoleNoteMoodImpl.insert(entity);
        } else {
            biuHoleNoteMoodImpl.restore(entity);
        }
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteMoodImpl().insert(entity);
    }

    public JsonDataResult<Map> addLabel(long userId, String name) {
        if (!wechatTool.filterContent(userId, name, 1).isStatus()) {
            return new JsonDataResult<>("输入信息违规");
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("tag", name);
        BiuLabelEntity entity = biuDbFactory.getHoleDbFactory().getLabelImpl().single(option);
        if (entity != null) {
            return new JsonDataResult<>("用户标签存在");
        }
        entity = new BiuLabelEntity();
        entity.setUserId(userId);
        entity.setTag(name);
        entity.setUseTime(new Date());
        biuDbFactory.getHoleDbFactory().getLabelImpl().insert(entity);
        Map<String, Object> result = new HashMap<>();
        result.put("id", entity.getId());
        result.put("tag", entity.getTag());
        return JsonDataResult.success(result);
    }

    public JsonDataResult<Map> removeLabel(long userId, long id) {
        BiuLabelEntity entity = biuDbFactory.getHoleDbFactory().getLabelImpl().find(id);
        if (entity == null) {
            return new JsonDataResult<>("标签不存在");
        }
        if (entity.getUserId() != userId) {
            return new JsonDataResult<>("非本人标签不可删除");
        }
        biuDbFactory.getHoleDbFactory().getLabelImpl().delete(entity);
        return JsonDataResult.success(new HashMap<>());
    }

    public Map getNoteFavorCondition(long noteId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("note_id", noteId);
        option.addCondition("comment_id", 0);
        return getFavorCondition(option);
    }

    public Map getFavorCondition(ProviderOption option) {
        Map<String, Object> result = new HashMap<>();
        List<String> images = new ArrayList<>();
        List<Long> usersList = new ArrayList<>();
        result.put("number", 0);
        result.put("images", images);
        result.put("users", usersList);
        List<BiuUserFavorEntity> favorList = biuDbFactory.getHoleDbFactory().getBiuUserFavorImpl().list(option);
        if (favorList == null) {
            return result;
        }
        if (favorList.size() == 0) {
            return result;
        }
        String users = favorList.stream().map(item -> item.getUserId()).map(value -> String.valueOf(value)).collect(Collectors.joining(","));
        ProviderOption userOption = new ProviderOption();
        userOption.setColumns("id,image,use_status");
        userOption.addCondition("id in (" + users + ")");
        List<BiuUserEntity> userList = biuDbFactory.getUserDbFactory().getBiuUserImpl().list(userOption);
        userList.forEach(item -> {
            if (item.getUseStatus() == BiuUserEntity.USER_AVAIL_STATUS) {
                images.add(parseImage(item.getImage()));
                usersList.add(item.getId());
            }
        });
        result.put("number", images.size());
        return result;
    }

    public List<String> getNoteImages(long noteId, int useType, int size) {
        ProviderOption option = new ProviderOption();
        option.setColumns("id,file");
        option.addCondition("use_type", useType);
        option.addCondition("relate_id", noteId);
        if (size > 0) {
            option.setUsePager(true);
            option.setLimit(size);
        }
        option.addOrderby("sort_index asc");
        List<BiuUserImageEntity> list = biuDbFactory.getUserDbFactory().getBiuUserImageImpl().list(option);
        if (list == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        list.forEach(item -> {
            result.add(parseImage(item.getFile()));
        });
        return result;
    }

    public boolean addNoteFavorMessage(long sourceId, long destId, long noteId, int relateType) {
        if (sourceId == destId) {
            return false;
        }
        BiuUserViewEntity user = getUserView(sourceId);
        String title = SystemOption.FAVOR_TITLE.getValue().replace("#NAME#", user.getPenName());
        ProviderOption option = new ProviderOption();
        option.addCondition("source_id", sourceId);
        option.addCondition("dest_id", destId);
        option.addCondition("relate_id", noteId);
        option.addCondition("relate_type", relateType);
        option.addCondition("message_type", BiuMessageEntity.MESSAGE_FAVOR);
        BiuMessageEntity message = biuDbFactory.getUserDbFactory().getBiuMessageImpl().single(option);
        if (message != null) {
            return false;
        }
        message = new BiuMessageEntity();
        message.setSourceId(sourceId);
        message.setDestId(destId);
        message.setMessageType(BiuMessageEntity.MESSAGE_FAVOR);
        message.setRelateId(noteId);
        message.setRelateType(relateType);
        message.setTitle(title);
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().insert(message);
        return true;
    }

    public boolean addNoteCommentMessage(long sourceId, long destId, long noteId) {
        if (sourceId == destId) {
            return false;
        }
        BiuUserViewEntity user = getUserView(sourceId);
        String title = SystemOption.COMMENT_TITLE.getValue().replace("#NAME#", user.getPenName());
        BiuMessageEntity message = new BiuMessageEntity();
        message.setSourceId(sourceId);
        message.setDestId(destId);
        message.setMessageType(BiuMessageEntity.MESSAGE_COMMENT);
        message.setRelateId(noteId);
        message.setRelateType(BiuMessageEntity.RELATE_NOTE_TYPE);
        message.setTitle(title);
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().insert(message);
        return true;
    }

    public boolean addNoteCommentReplyMessage(long sourceId, long destId, long commentId) {
        if (sourceId == destId) {
            return false;
        }
        BiuUserViewEntity user = getUserView(sourceId);
        String title = SystemOption.COMMENT_REPLY_TITLE.getValue().replace("#NAME#", user.getPenName());
        BiuMessageEntity message = new BiuMessageEntity();
        message.setSourceId(sourceId);
        message.setDestId(destId);
        message.setMessageType(BiuMessageEntity.MESSAGE_REPLY);
        message.setRelateId(commentId);
        message.setRelateType(BiuMessageEntity.RELATE_NOTE_COMMENT_TYPE);
        message.setTitle(title);
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().insert(message);
        return true;
    }

    public boolean isFavored(long userId, long noteId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("note_id", noteId);
        BiuUserFavorEntity entity = biuDbFactory.getHoleDbFactory().getBiuUserFavorImpl().single(option);
        return entity != null ? true : false;
    }

    public boolean isCommented(long userId, long noteId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("note_id", noteId);
        BiuHoleNoteCommentEntity entity = biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().single(option);
        return entity != null ? true : false;
    }

    public BiuUserReadLogEntity getUserReadLog(long userId, long relateId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("relate_id", relateId);
        return biuDbFactory.getUserDbFactory().getBiuUserReadLogImpl().single(option);
    }

    public void execSyncUserIndex(long userId) {
        BiuUserViewEntity user = getUserView(userId);
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        if (user == null) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getLockStatus() > 0) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (new Date().getTime() - user.getSortTime().getTime() >= 86400 * 14 * 1000) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getSearchStatus() != BiuUserViewEntity.SEARCH_OPEN_STATUS || user.getUseStatus() != BiuUserViewEntity.USER_AVAIL_STATUS) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getTitle() == null || (user.getTitle() != null && user.getTitle().isEmpty())) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getIntroduce() == null || (user.getIntroduce() != null && user.getIntroduce().isEmpty())) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getSelfInterest() == null || (user.getSelfInterest() != null && user.getSelfInterest().isEmpty())) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getSelfCommunicate() == null || (user.getSelfCommunicate() != null && user.getSelfCommunicate().isEmpty())) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getSelfCommunicate().contains("1") && (user.getAddress() == null || (user.getAddress() != null && user.getAddress().isEmpty()))) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getSelfCommunicate().contains("2") && (user.getEmail() == null || (user.getEmail() != null && user.getEmail().isEmpty()))) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getSearchCommunicate() == null || (user.getSearchCommunicate() != null && user.getSearchCommunicate().isEmpty())) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        if (user.getSearchSex() == null || (user.getSearchSex() != null && user.getSearchSex().isEmpty())) {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().destroy(option);
            return ;
        }
        BiuUserIndexViewEntity indexEntity = biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().single(option);

        boolean isUpdate = true;
        if (indexEntity == null) {
            indexEntity = new BiuUserIndexViewEntity();
            isUpdate = false;
        }
        indexEntity.setUserId(user.getId());
        indexEntity.setUserCardno(user.getUserCardno());
        indexEntity.setUsername(user.getUsername());
        indexEntity.setNick(user.getNick());
        indexEntity.setImage(user.getImage());
        indexEntity.setPenName(user.getPenName());
        indexEntity.setOpenid(user.getOpenid());
        indexEntity.setUnionid(user.getUnionid());
        indexEntity.setSex(user.getSex());
        indexEntity.setMatchStartAge(user.getMatchStartAge());
        indexEntity.setMatchEndAge(user.getMatchEndAge());
        indexEntity.setPhone(user.getPhone());
        indexEntity.setEmail(user.getEmail());
        indexEntity.setProvince(user.getProvince());
        indexEntity.setCity(user.getCity());
        indexEntity.setCountry(user.getCountry());
        indexEntity.setStreet(user.getStreet());
        indexEntity.setAddress(user.getAddress());
        indexEntity.setZipcode(user.getZipcode());
        indexEntity.setTitle(user.getTitle());
        indexEntity.setIntroduce(user.getIntroduce());
        indexEntity.setLastIp(user.getLastIp());
        indexEntity.setRemark(user.getRemark());
        indexEntity.setUseStatus(user.getUseStatus());
        indexEntity.setCommentStatus(user.getCommentStatus());
        indexEntity.setSearchStatus(user.getSearchStatus());
        indexEntity.setAnonymous(user.getAnonymous());
        indexEntity.setLastLogin(user.getLastLogin());
        indexEntity.setSortTime(user.getSortTime());
        indexEntity.setBirthdayYear(user.getBirthdayYear());
        indexEntity.setIsPenuser(user.getIsPenuser());
        indexEntity.setAge(user.getAge());
        indexEntity.setCollectNum(user.getCollectNum());
        indexEntity.setSelfInterest(user.getSelfInterest());
        indexEntity.setLockStatus(user.getLockStatus());
        String search_communicate = user.getSearchCommunicate();
        if (search_communicate != null && !search_communicate.isEmpty()) {
            indexEntity.setSearchCommunicate(Arrays.asList(search_communicate.replace("'", "").split(",")).stream().mapToInt(Integer::parseInt).reduce(Integer::sum).orElse(0));
        } else {
            indexEntity.setSearchCommunicate(0);
        }
        String self_communicate = user.getSelfCommunicate();
        if (self_communicate != null && !self_communicate.isEmpty()) {
            indexEntity.setSelfCommunicate(Arrays.asList(self_communicate.replace("'", "").split(",")).stream().mapToInt(Integer::parseInt).reduce(Integer::sum).orElse(0));
        } else {
            indexEntity.setSelfCommunicate(0);
        }
        String search_sex = user.getSearchSex();
        if (search_sex != null && !search_sex.isEmpty()) {
            indexEntity.setSearchSex(Arrays.asList(search_sex.replace("'", "").split(",")).stream().mapToInt(Integer::parseInt).reduce(Integer::sum).orElse(0));
        } else {
            indexEntity.setSearchSex(0);
        }
        indexEntity.setProtectedUser(user.getProtectedUser());
        if (isUpdate) {
//            indexEntity.setCreatedAt(user.getCreatedAt());
//            indexEntity.setUpdatedAt(user.getUpdatedAt());
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().update(indexEntity);
        } else {
            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().insert(indexEntity);
//            biuDbFactory.getUserDbFactory().getBiuUserIndexViewImpl().update(indexEntity);
        }
    }

    public void syncUserIndex(long userId) {
        String key = TaskOption.USER_INDEX_SYNC.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        operator.rightPush(key, String.valueOf(userId));
    }

    public BiuUserEntity initUserZipcode(BiuUserEntity user) {
        String code = "";
        if (user.getAddress() != null && !user.getAddress().isEmpty()) {
            ProviderOption option = new ProviderOption();
            Map<String, String> areaInfo = AddressTool.addressResolution(user.getAddress());
            if (areaInfo.containsKey("county") && !areaInfo.get("county").isEmpty()) {
                option.addCondition("area_name", areaInfo.get("county"));
                option.addCondition("area_type", 3);
                BiuAreaEntity area = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
                if (area != null) {
                    code = area.getCode();
                }
            } else if (areaInfo.containsKey("city") && !areaInfo.get("city").isEmpty()) {
                option.addCondition("area_name", areaInfo.get("city"));
                option.addCondition("area_type", 2);
                BiuAreaEntity area = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
                if (area != null) {
                    code = area.getCode();
                }
            } else if (areaInfo.containsKey("province") && !areaInfo.get("province").isEmpty()) {
                option.addCondition("area_name", areaInfo.get("province"));
                option.addCondition("area_type", 1);
                BiuAreaEntity area = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
                if (area != null) {
                    code = area.getCode();
                }
            }
        }
        if (code.isEmpty()) {
            if (user.getCountry() != null && !user.getCountry().isEmpty()) {
                code = user.getCountry();
            } else if(user.getCity() != null && !user.getCity().isEmpty()) {
                code = user.getCity();
            } else if(user.getProvince() != null && !user.getProvince().isEmpty()) {
                code = user.getProvince();
            }
        }
        if (code.isEmpty()) {
            return user;
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("code", code);
        BiuAreaEntity area = biuDbFactory.getCommonDbFactory().getBiuAreaImpl().single(option);
        if (area != null && !area.getZipcode().isEmpty()) {
            user.setZipcode(area.getZipcode());
        }
        return user;
    }

    public void sendMiniMessage(long... values) {
        String message_value = Arrays.stream(values).boxed().map(item -> String.valueOf(item)).collect(Collectors.joining("_"));
        String key = TaskOption.USER_WECHAT_MESSAGE.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        operator.rightPush(key, message_value);
    }

    public void filterByWechat(long... values) {
        if (env != null && env.equals("dev")) {
            return ;
        }
        String message_value = Arrays.stream(values).boxed().map(item -> String.valueOf(item)).collect(Collectors.joining("_"));
        String key = TaskOption.WECHAT_FILTER.getValue();
        ListOperator operator = biuRedisFactory.getBiuRedisTool().getListOperator();
        operator.rightPush(key, message_value);
    }
}
