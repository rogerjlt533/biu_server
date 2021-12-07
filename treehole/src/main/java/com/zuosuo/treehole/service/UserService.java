package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.mybatis.provider.CheckStatusEnum;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.result.FriendCommunicateInfo;
import com.zuosuo.treehole.result.UserFriendCommunicateInfo;
import com.zuosuo.treehole.result.UserFriendResult;
import com.zuosuo.treehole.result.UserInterestResult;
import com.zuosuo.treehole.tool.HashTool;
import com.zuosuo.treehole.tool.QiniuTool;
import org.springframework.beans.factory.annotation.Autowired;
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

    public BiuUserEntity getUserByOpenid(String openid) {
        ProviderOption option = new ProviderOption();
        option.addCondition("openid", openid);
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
            result.add(new UserInterestResult(item.getId(), item.getTag(), 1));
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
            result.add(new UserInterestResult(item.getId(), item.getTag(), interests.contains(item.getId()) ? 1 : 0));
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

    public long clearUserImage(long userId, int type) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().destroy(option);
    }

    public BiuUserImageEntity getUserImage(long userId, String file) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("file", file);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public BiuUserImageEntity getUserImage(long userId, int type, String file, CheckStatusEnum status) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        option.addCondition("file", file);
        option.setStatus(status.getValue());
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public void setUserImage(long userId, int type, List<String> files) {
        clearUserImage(userId, type);
        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                setUserImage(userId, type, files.get(i), i + 1);
            }
        }
    }

    public BiuUserImageEntity setUserImage(long userId, int type, String file, int sort) {
        boolean isQiniu = false;
        if (file.contains("/upload")) {
            file = file.substring(file.indexOf("/upload") + 1);
            isQiniu = true;
        }
        BiuUserImageEntity image = getUserImage(userId, type, file, CheckStatusEnum.DELETED);
        if (image == null) {
            image = new BiuUserImageEntity();
            image.setUserId(userId);
            image.setUseType(type);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().insert(image);
        } else {
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().restore(image);
        }
        if (isQiniu) {
            BiuUserImageEntity relate = getUserImage(userId, file);
            image.setFile(relate.getFile());
            image.setUseType(type);
            image.setHashCode(relate.getHashCode());
            image.setSortIndex(sort);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().update(image);
            return image;
        } else {
            image.setFile(file);
            image.setUseType(type);
            image.setHashCode("");
            image.setSortIndex(sort);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().update(image);
            return image;
        }
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
        return friend;
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
        addUserMessage(userId, userId, BiuMessageEntity.NOTICE_APPLY, friendId, "正在向@" + friendUser.getPenName() + "提交笔友申请", "");
        addUserMessage(userId, friendId, BiuMessageEntity.NOTICE_APPLY, userId, "@" + user.getPenName() + "正在向您提交笔友申请", "");
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
        BiuUserViewEntity friendUser = getUserView(friendId);
        BiuUserFriendEntity passed = getUserFriend(userId, friendId, BiuUserFriendEntity.PASS_STATUS);
        if (passed != null) {
            return new JsonResult("已通过申请");
        }
        BiuUserFriendEntity waiting = getUserFriend(userId, friendId, BiuUserFriendEntity.WAITING_STATUS);
        if (waiting == null) {
            return new JsonResult("非有效好友申请");
        }
        BiuUserFriendMemberEntity member = getUserFriendMember(waiting.getId(), friendId, BiuUserFriendMemberEntity.WAITING_STATUS);
        if (member == null) {
            return new JsonResult("无有效好友申请审核");
        }
        member.setConfirmStatus(BiuUserFriendMemberEntity.PASS_STATUS);
        member.setConfirmTime(new Date());
        biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().update(member);
        waiting.setConfirmStatus(BiuUserFriendEntity.PASS_STATUS);
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().update(waiting);
        addUserMessage(friendId, userId, BiuMessageEntity.NOTICE_FRIEND, friendId, "@" + friendUser.getPenName() + "已同意添加您为笔友", "");
        return JsonResult.success();
    }

    public JsonResult refuseFriend(long userId, long friendId) {
        BiuUserViewEntity friendUser = getUserView(friendId);
        BiuUserFriendEntity refuseed = getUserFriend(userId, friendId, BiuUserFriendEntity.REFUSE_STATUS);
        if (refuseed != null) {
            return new JsonResult("已拒绝申请");
        }
        BiuUserFriendEntity waiting = getUserFriend(userId, friendId, BiuUserFriendEntity.WAITING_STATUS);
        if (waiting == null) {
            return new JsonResult("非有效好友申请");
        }
        BiuUserFriendMemberEntity member = getUserFriendMember(waiting.getId(), friendId, BiuUserFriendMemberEntity.WAITING_STATUS);
        if (member == null) {
            return new JsonResult("无有效好友申请审核");
        }
        member.setConfirmStatus(BiuUserFriendMemberEntity.REFUSE_STATUS);
        member.setConfirmTime(new Date());
        biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().update(member);
        waiting.setConfirmStatus(BiuUserFriendEntity.REFUSE_STATUS);
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().update(waiting);
        addUserMessage(friendId, userId, BiuMessageEntity.NOTICE_FRIEND, friendId, "@" + friendUser.getPenName() + "已拒绝添加您为笔友", "");
        return JsonResult.success();
    }

    public String getUserAddress(long userId) {
        BiuUserViewEntity user = getUserView(userId);
        List<String> address = new ArrayList<>();
        String province = areaService.getArea(user.getProvince());
        if (!province.isEmpty()) {
            address.add(province);
        }
        String city = areaService.getArea(user.getCity());
        if (!city.isEmpty()) {
            address.add(city);
        }
        String country = areaService.getArea(user.getCountry());
        if (!country.isEmpty()) {
            address.add(country);
        }
        if (!user.getAddress().isEmpty()) {
            address.add(user.getAddress());
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

    public void initFriendCommunicate(BiuUserFriendEntity friend, BiuUserViewEntity member, UserFriendCommunicateInfo info) {
        if (friend.getCommunicateType() == BiuUserCommunicateEntity.COM_METHOD_LETTER) {
            info.getInfo().put("name", member.getUsername());
            info.getInfo().put("phone", member.getPhone());
            info.getInfo().put("address", getUserAddress(member.getId()));
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
            List<Long> users = Arrays.asList(friend.getUsers().split(",")).stream().map(item -> Long.parseLong(item)).collect(Collectors.toList());
            long friendId = 0;
            for (long memberId: users) {
                if (memberId != userId) {
                    friendId = memberId;
                }
            }
            unit.setFriend(encodeHash(friendId));
            BiuUserViewEntity member = getUserView(friendId);
            unit.setName(member.getPenName());
            unit.setImage(parseImage(member.getImage()));
            List<String> descList = new ArrayList<>();
            String province = areaService.getArea(member.getProvince());
            if (!province.isEmpty()) {
                descList.add(province);
            }
            String sex = member.getSexTag();
            if (!sex.isEmpty()) {
                descList.add(sex);
            }
            int age = member.getAge();
            if (age > 0) {
                descList.add(age + "岁");
            }
            unit.setDesc(String.join("/", descList));
            initFriendCommunicate(friend, member, unit.getCommunicateInfo());
            if (friend.getLastLog() > 0) {
                BiuUserFriendCommunicateLogEntity log = biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().find(friend.getLastLog());
                if (log.getReceiveStatus() == 0 && log.getReceiveUser() == userId) {
                    unit.getCommunicateInfo().setAllowReceive(1);
                }
                unit.getCommunicateInfo().setLogId(encodeHash(log.getId()));
                unit.getCommunicateInfo().setReceived(log.getReceiveStatus());
                unit.getCommunicateInfo().setLabel(log.getReceiveStatus() == 0? "笔友已寄出邮件": "笔友已收到邮件");
                unit.getCommunicateInfo().setSendTag("邮件已寄出");
                unit.getCommunicateInfo().setReceiveTag("邮件已接收");
                unit.getCommunicateInfo().setLogTime(TimeTool.formatDate(log.getCreatedAt(), "yyyy/MM/dd"));
            }
            list.add(unit);
        });
        return list;
    }

    public void sendFriendCommunicate(BiuUserFriendEntity friend, long sendUser) {
        long receiveUser = 0;
        List<Long> users = Arrays.asList(friend.getUsers().split(",")).stream().map(item -> Long.parseLong(item)).collect(Collectors.toList());
        for (long item: users) {
            if (item != sendUser) {
                receiveUser = item;
            }
        }
        BiuUserFriendCommunicateLogEntity log = new BiuUserFriendCommunicateLogEntity();
        log.setFriendId(friend.getId());
        log.setCommunicateType(friend.getCommunicateType());
        log.setSendUser(sendUser);
        log.setReceiveUser(receiveUser);
        biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().insert(log);
        friend.setLastLog(log.getId());
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().update(friend);
        BiuUserViewEntity sender = getUserView(sendUser);
        addUserMessage(sendUser, receiveUser, BiuMessageEntity.NOTICE_SEND, log.getId(), "笔友@" + sender.getPenName() + "信件已发出", "");
    }

    public void receiveFriendCommunicate(BiuUserFriendCommunicateLogEntity log) {
        log.setReceiveStatus(BiuUserFriendCommunicateLogEntity.RECEIVED);
        log.setReceiveTime(new Date());
        biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().update(log);
        BiuUserViewEntity receiver = getUserView(log.getReceiveUser());
        addUserMessage(log.getReceiveUser(), log.getSendUser(), BiuMessageEntity.NOTICE_RECEIVE, log.getId(), "笔友@" + receiver.getPenName() + "已收到信件", "");
    }
}
