package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.impl.BiuUserBlacklistImpl;
import com.zuosuo.biudb.impl.BiuUserCollectImpl;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.service.AreaService;
import com.zuosuo.treehole.service.KeywordService;
import com.zuosuo.treehole.service.UserCollectService;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.tool.HashTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class CombineAccountProcessor {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCollectService userCollectService;
    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private HashTool hashTool;
    @Autowired
    private AreaService areaService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private BiuRedisFactory biuRedisFactory;

    public void combineUserByOpenId(String openid) {
        ProviderOption option = new ProviderOption();
        option.addCondition("openid", openid);
        option.addCondition("use_status", 1);
        option.addOrderby("last_login desc");
        option.setLimit(2);
        List<BiuUserEntity> account_list = biuDbFactory.getUserDbFactory().getBiuUserImpl().list(option);
        combineUsers(account_list.get(0), account_list.get(1));
    }

    private void combineUsers(BiuUserEntity dest, BiuUserEntity source) {
        if (dest == null || source == null) {
            return;
        }
        processUserBlack(dest, source);
        processUserCollect(dest, source);
        processUserPublicMessage(dest, source);
        processUserFriend(dest, source);
        processUserNote(dest, source);
        source.setUseStatus(0);
        biuDbFactory.getUserDbFactory().getBiuUserImpl().update(source);
    }

    /**
     * 处理用户拉黑
     *
     * @param dest
     * @param source
     */
    private void processUserBlack(BiuUserEntity dest, BiuUserEntity source) {
        ProviderOption option = new ProviderOption();
        option.addCondition("(user_id=" + source.getId() + " or black_id=" + source.getId() + ")");
        BiuUserBlacklistImpl impl = biuDbFactory.getUserDbFactory().getBiuUserBlacklistImpl();
        List<BiuUserBlacklistEntity> list = impl.list(option);
        list.forEach(item -> {
            if ((item.getUserId() == dest.getId() && item.getBlackId() == source.getId()) || (item.getUserId() == source.getId() && item.getBlackId() == dest.getId())) {
                impl.delete(item);
            } else if (item.getBlackId() == source.getId()) {
                ProviderOption where = new ProviderOption();
                where.addCondition("user_id", item.getUserId());
                where.addCondition("black_id", dest.getId());
                if (impl.single(where) != null) {
                    impl.delete(item);
                } else {
                    item.setBlackId(dest.getId());
                    impl.update(item);
                }
            } else if (item.getUserId() == source.getId()) {
                ProviderOption where = new ProviderOption();
                where.addCondition("black_id", item.getUserId());
                where.addCondition("user_id", dest.getId());
                if (impl.single(where) != null) {
                    impl.delete(item);
                } else {
                    item.setUserId(dest.getId());
                    impl.update(item);
                }
            }
        });
    }

    /**
     * 处理用户关注
     *
     * @param dest
     * @param source
     */
    private void processUserCollect(BiuUserEntity dest, BiuUserEntity source) {
        ProviderOption option = new ProviderOption();
        option.addCondition("(user_id=" + source.getId() + " or relate_id=" + source.getId() + ")");
        BiuUserCollectImpl impl = biuDbFactory.getUserDbFactory().getBiuUserCollectImpl();
        List<BiuUserCollectEntity> list = impl.list(option);
        list.forEach(item -> {
            if ((item.getUserId() == dest.getId() && item.getRelateId() == source.getId()) || (item.getUserId() == source.getId() && item.getRelateId() == dest.getId())) {
                impl.delete(item);
            } else if (item.getRelateId() == source.getId()) {
                ProviderOption where = new ProviderOption();
                where.addCondition("user_id", item.getUserId());
                where.addCondition("relate_id", dest.getId());
                if (impl.single(where) != null) {
                    impl.delete(item);
                } else {
                    item.setRelateId(dest.getId());
                    impl.update(item);
                }
            } else if (item.getUserId() == source.getId()) {
                ProviderOption where = new ProviderOption();
                where.addCondition("relate_id", item.getUserId());
                where.addCondition("user_id", dest.getId());
                if (impl.single(where) != null) {
                    impl.delete(item);
                } else {
                    item.setUserId(dest.getId());
                    impl.update(item);
                }
            }
        });
    }

    /**
     * 处理公告消息
     *
     * @param dest
     * @param source
     */
    private void processUserPublicMessage(BiuUserEntity dest, BiuUserEntity source) {
        ProviderOption option = new ProviderOption();
        option.addCondition("dest_id", source.getId());
        option.addCondition("message_type in (" + Arrays.asList(BiuMessageEntity.PUBLIC_NOTICE, BiuMessageEntity.PUBLIC_ACTIVE, BiuMessageEntity.PUBLIC_UPDATE)
                .stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
        option.setAttribute("dest_id", dest.getId());
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().modify(option);
    }

    /**
     * 处理好友关系及消息
     * @param dest
     * @param source
     */
    private void processUserFriend(BiuUserEntity dest, BiuUserEntity source) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", source.getId());
        option.setColumns("friend_id");
        List<BiuUserFriendMemberEntity> members = biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().list(option);
        if (members.isEmpty()) {
            return ;
        }
        option = new ProviderOption();
        option.addCondition("id in (" + members.stream().map(item -> String.valueOf(item.getFriendId())).collect(Collectors.joining(",")) + ")");
        option.addCondition("confirm_status in (0,1)");
        List<BiuUserFriendEntity> list = biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().list(option);
        list.forEach(item -> {
            List<Long> users = Arrays.asList(item.getUsers().split(",")).stream().map(value -> Long.valueOf(value)).collect(Collectors.toList());
            if (users.contains(dest.getId()) && users.contains(source.getId())) {
                // 自身账号直接删除关系
                userService.removeFriendMessage(item.getId());
                biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().delete(item);
            } else {
                long friendId = userService.getFriendId(item, source.getId());
                ProviderOption where = new ProviderOption();
                where.addCondition("FIND_IN_SET('" + dest.getId() + "', users)");
                where.addCondition("FIND_IN_SET('" + friendId + "', users)");
                where.addCondition("confirm_status in (0,1)");
                BiuUserFriendEntity destFriend = biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().single(where);
                if (destFriend == null) {
                    // 目标无该记录直接变更
                    changeFriendMember(item, source.getId(), dest.getId());
                } else if (destFriend.getConfirmStatus() == BiuUserFriendEntity.PASS_STATUS) {
                    // 已通过，直接删除
                    userService.removeFriendMessage(item.getId());
                    biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().delete(item);
                } else if (item.getConfirmStatus() == BiuUserFriendEntity.WAITING_STATUS) {
                    // 都待通过，直接删除
                    userService.removeFriendMessage(item.getId());
                    biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().delete(item);
                } else {
                    // 变更来源的为目标的，并将目标的删除
                    // 变更来源的为目标的
                    changeFriendMember(item, source.getId(), dest.getId());
                    // 将目标的删除
                    userService.removeFriendMessage(destFriend.getId());
                    biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().delete(destFriend);
                }
            }
        });
    }

    /**
     * 更换好友成员
     * @param entity
     * @param source
     * @param dest
     */
    private void changeFriendMember(BiuUserFriendEntity entity, long source, long dest) {
        ProviderOption where = new ProviderOption();
        where.addCondition("friend_id", entity.getId());
        List<BiuUserFriendMemberEntity> members = biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().list(where);
        long friendId = 0;
        for (BiuUserFriendMemberEntity member: members) {
            if (member.getUserId() == source) {
                member.setUserId(dest);
                biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().update(member);
            } else {
                friendId = member.getUserId();
            }
        }
        entity.setUsers(userService.formatUserFriendMembers(dest, friendId));
        biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().update(entity);
        ProviderOption option = new ProviderOption();
        option.addCondition("friend_id", entity.getId());
        option.addCondition("message_type in (" + Arrays.asList(BiuMessageEntity.NOTICE_APPLY, BiuMessageEntity.NOTICE_FRIEND, BiuMessageEntity.PRIVATE_MESSAGE).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
        List<BiuMessageEntity> messages = biuDbFactory.getUserDbFactory().getBiuMessageImpl().list(option);
        messages.forEach(message -> {
            if (message.getDestId() == source) {
                message.setDestId(dest);
            }
            if (message.getSourceId() == source) {
                message.setSourceId(dest);
            }
            if (message.getRelateId() == source && (message.getMessageType() == BiuMessageEntity.NOTICE_APPLY || message.getMessageType() == BiuMessageEntity.NOTICE_FRIEND)) {
                message.setRelateId(dest);
            }
            biuDbFactory.getUserDbFactory().getBiuMessageImpl().update(message);
        });
        option = new ProviderOption();
        option.addCondition("friend_id", entity.getId());
        option.addCondition("message_type in (" + Arrays.asList(BiuMessageEntity.NOTICE_SEND, BiuMessageEntity.NOTICE_RECEIVE).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
        List<BiuMessageEntity> log_messages = biuDbFactory.getUserDbFactory().getBiuMessageImpl().list(option);
        log_messages.forEach(message -> {
            if (message.getDestId() == source) {
                message.setDestId(dest);
            }
            if (message.getSourceId() == source) {
                message.setSourceId(dest);
            }
            biuDbFactory.getUserDbFactory().getBiuMessageImpl().update(message);
        });
    }

    /**
     * 处理用户树洞
     * @param dest
     * @param source
     */
    private void processUserNote(BiuUserEntity dest, BiuUserEntity source) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", source.getId());
        option.setAttribute("user_id", dest.getId());
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().modify(option);
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().modify(option);
        option = new ProviderOption();
        option.addCondition("(dest_id=" + source.getId() + " or source_id=" + source.getId() + ")");
        option.addCondition("message_type in (" + Arrays.asList(BiuMessageEntity.MESSAGE_COMMENT, BiuMessageEntity.MESSAGE_FAVOR, BiuMessageEntity.MESSAGE_REPLY)
                .stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
        option.setAttribute("dest_id", dest.getId());
        List<BiuMessageEntity> messages = biuDbFactory.getUserDbFactory().getBiuMessageImpl().list(option);
        messages.forEach(message -> {
            if (message.getSourceId() == source.getId()) {
                message.setSourceId(dest.getId());
            }
            if (message.getDestId() == source.getId()) {
                message.setDestId(dest.getId());
            }
            biuDbFactory.getUserDbFactory().getBiuMessageImpl().update(message);
        });
        option = new ProviderOption();
        option.addCondition("(user_id=" + source.getId() + " or relate_id=" + source.getId() + ")");
        List<BiuUserFavorEntity> favorList = biuDbFactory.getHoleDbFactory().getBiuUserFavorImpl().list(option);
        favorList.forEach(favor -> {
            if (favor.getUserId() == source.getId()) {
                favor.setUserId(dest.getId());
            }
            if (favor.getRelateId() == source.getId()) {
                favor.setRelateId(dest.getId());
            }
            biuDbFactory.getHoleDbFactory().getBiuUserFavorImpl().update(favor);
        });
    }

}
