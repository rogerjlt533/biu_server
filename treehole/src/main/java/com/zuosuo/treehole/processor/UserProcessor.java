package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.impl.*;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.response.ResponseConfig;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.time.TimeFormat;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.CommonTool;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.mybatis.tool.PageTool;
import com.zuosuo.treehole.bean.*;
import com.zuosuo.treehole.config.SystemOption;
import com.zuosuo.treehole.result.*;
import com.zuosuo.treehole.service.AreaService;
import com.zuosuo.treehole.service.KeywordService;
import com.zuosuo.treehole.service.UserCollectService;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.tool.HashTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserProcessor {

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

    public UserService getUserService() {
        return userService;
    }

    public KeywordService getKeywordService() {
        return keywordService;
    }

    /**
     * 更新用户信息
     * @param id
     * @param bean
     * @return
     */
    public FuncResult initUserInfo(long id, UserInitUpdateInfoBean bean) {
        BiuUserEntity user = userService.find(id);
        if (user == null) {
            return new FuncResult(false, "无对应用户记录");
        }
        user.setNick(bean.getNick().trim());
        user.setImage(bean.getImage().trim());
        BiuUserImageEntity image = userService.setUserImage(id, BiuUserImageEntity.USE_TYPE_AVATOR, 0, bean.getImage(), 0);
        user.setImage(image.getFile());
        biuDbFactory.getUserDbFactory().getBiuUserImpl().update(user);
        return new FuncResult(true);
    }

    /**
     * 获取用户信息列表
     * @param id
     * @param bean
     * @return
     */
    public FuncResult getList(long id, UserListBean bean) {
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
//        result.put("count", impl.count(option));
        result.put("more", 0);
        List<UserResult> userList = new ArrayList<>();
        result.put("list", userList);
        result.put("last", "");
        BiuUserViewEntity user = userService.getUserView(id);
        ProviderOption option = new ProviderOption();
        option.setUsePager(true);
//        option.setWriteLog(true);
        option.addCondition("use_status", BiuUserViewEntity.USER_AVAIL_STATUS);
        option.addCondition("search_status", BiuUserViewEntity.SEARCH_OPEN_STATUS);
        // 自定义
        int sex = bean.getSex();
        if (sex > 0) {
            option.addCondition("sex", sex);
        }
        int com_method = bean.getCommunicate();
        if (com_method > 0) {
            option.addCondition("FIND_IN_SET(\"'" + com_method + "'\", self_communicate)");
        }
        int[] ages = bean.getAge();
        if (ages != null) {
            option.addCondition("age>=" + ages[0] + " and age<=" + ages[1]);
        }
        BiuUserViewEntity lastUser = null;
        String last = bean.getLast();
        if (!last.isEmpty()) {
            long lastId = decodeHash(last);
            if (lastId > 0) {
                lastUser = userService.getUserView(lastId);
            }
        }
        if(user != null && bean.getMethod() == UserListBean.RECOMMEND) {
//            option.addCondition("id!=" + user.getId());
            int self_age = user.getAge();
            if (self_age > 0) {
                option.addCondition("match_start_age<=" + self_age + " and match_end_age>=" + self_age);
            }
            int self_sex = user.getSex();
            if (self_sex > 0) {
                option.addCondition("FIND_IN_SET(\"'" + self_sex + "'\", search_sex)");
            }
            String self_communicate = user.getSelfCommunicate() != null ? user.getSelfCommunicate() : "";
            String[] communicates = self_communicate.split(",");
            if (communicates.length > 0) {
                List<String> communicateCondition = new ArrayList<>();
                for (String communicate: communicates) {
                    if (!communicate.isEmpty()) {
                        communicateCondition.add("FIND_IN_SET(\"" + communicate + "\", search_communicate)");
                    }
                }
                if (!communicateCondition.isEmpty()) {
                    option.addCondition("(" + String.join(" or ", communicateCondition) + ")");
                }
            }
            option.addCondition("(!FIND_IN_SET(\"'" + user.getId() + "'\", protected_user) or ISNULL(protected_user))");
            System.out.println(user.getProtectedUser());
            if (user.getProtectedUser() != null && !user.getProtectedUser().isEmpty()) {
                option.addCondition("!FIND_IN_SET(concat(\"'\", id, \"'\"), \"" + user.getProtectedUser() +"\")");
            }
        }
        if(user == null && bean.getMethod() == UserListBean.RECOMMEND) {
            // 非登录下系统推荐无数据
            return new FuncResult(false, "无对应记录", result);
        }
        // 两个月内登录的
        option.addCondition("sort_time>='" + TimeTool.formatDate(TimeTool.getOffsetDate(new Date(), new DiscTime().setMonth(-2))) + "'");
        // 最后记录排重
        if (lastUser != null) {
            option.addCondition("sort_time<'" + TimeTool.formatDate(lastUser.getSortTime()) + "'");
        }
        option.addOrderby("sort_time desc");
        option.setOffset(PageTool.getOffset(bean.getPage(), bean.getSize()));
        option.setLimit(bean.getSize() + 1);
        BiuUserViewImpl impl = biuDbFactory.getUserDbFactory().getBiuUserViewImpl();
        List<BiuUserViewEntity> list = impl.list(option);
        if (list.size() > bean.getSize()) {
            list.remove(list.size() - 1);
            result.put("more", 1);
        }
        if (list.isEmpty()) {
            return new FuncResult(false, "无对应记录", result);
        }
        result.put("last", encodeHash(list.get(list.size() - 1).getId()));
        userList = processList(list, user);
        result.put("list", userList);
        System.out.println(userList.size());
        return new FuncResult(true, "", result);
    }

    /**
     * 处理用户列表信息
     * @param list
     * @param user
     * @return
     */
    private List<UserResult> processList(List<BiuUserViewEntity> list, BiuUserViewEntity user) {
        List<UserResult> result = new ArrayList<>();
        list.forEach(item -> {
            UserResult unit = new UserResult();
            unit.setId(encodeHash(item.getId()));
            unit.setSelf(user != null && user.getId() == item.getId() ? 1 : 0);
            unit.setImage(userService.parseImage(item.getImage()));
            unit.setTitle(item.getTitle());
            unit.setIntroduce(item.getIntroduce());
            unit.setName(item.getPenName());
            List<String> desc = new ArrayList<>();
            if (!areaService.getArea(item.getProvince()).isEmpty()) {
                desc.add(areaService.getArea(item.getProvince()));
            }
            if (!item.getSexTag().isEmpty()) {
                desc.add(item.getSexTag());
            }
            if (item.getAge() > 0) {
                desc.add(item.getAge() + "岁");
            }
            unit.setDesc(String.join("/", desc));
            unit.setSortTime(TimeTool.friendlyTime(item.getSortTime()));
            unit.setImages(userService.getUserImageList(item.getId(), BiuUserImageEntity.USE_TYPE_INTRODUCE));
            unit.setInterests(userService.getUserInterests(item.getId()));
            if (item.getSelfCommunicate() != null && !item.getSelfCommunicate().isEmpty()) {
                unit.setCommunicate(Arrays.asList(item.getSelfCommunicate().replace("'", "").split(",")).stream().mapToInt(Integer::parseInt).reduce(Integer::sum).orElse(0));
            } else {
                unit.setCommunicate(3);
            }
            if (user != null) {
                unit.setIsCollect(userCollectService.isCollected(user.getId(), item.getId()) ? 1 : 0);
                if (user.getId() != item.getId()) {
                    BiuUserReadLogEntity readLog = userService.getUserReadLog(user.getId(), item.getId());
                    unit.setIsRead(readLog != null ? 1 : 0);
                }
            }
            result.add(unit);
        });
        return result;
    }

    /**
     * 获取用户信息
     */
    public FuncResult getUserInfo(long userId) {
        BiuUserViewEntity user = userService.getUserView(userId);
        if (user == null) {
            return new FuncResult(false, "");
        }
        MyInfoResult result = new MyInfoResult();
        result.setId(encodeHash(user.getId()));
        result.setNick(user.getNick());
        result.setCardno(user.getUserCardno());
        result.setUsername(user.getUsername());
        result.setPenName(user.getPenName());
        result.setSex(user.getSex());
        result.setSexTag(user.getSexTag());
        result.setImage(userService.parseImage(user.getImage()));
        result.setBirthdayYear(user.getBirthdayYear());
        result.getProvince().setCode(user.getProvince());
        result.getProvince().setName(areaService.getArea(user.getProvince()));
        result.getCity().setCode(user.getCity());
        result.getCity().setName(areaService.getArea(user.getCity()));
        result.getCountry().setCode(user.getCountry());
        result.getCountry().setName(areaService.getArea(user.getCountry()));
        result.setAddress(user.getAddress());
        List<Integer> communicates = CommonTool.parseList(user.getSelfCommunicate() != null && !user.getSelfCommunicate().isEmpty() ? user.getSelfCommunicate().replace("'", "").split(",") : new String[]{}, item -> Integer.valueOf(item));
        result.getCommunicates().setValue(communicates.stream().reduce(Integer::sum).orElse(0));
        result.getCommunicates().setTag(userService.parseCommunicates(communicates));
        List<UserInterestResult> interests = userService.getUserInterestList(user.getId());
        result.getInterests().setList(interests);
        result.getInterests().setTag(userService.parseInterests(interests));
        result.setTitle(user.getTitle());
        result.setIntroduce(user.getIntroduce());
        result.setPhone(user.getPhone());
        result.setEmail(user.getEmail());
        result.setZipcode(user.getZipcode());
        List<Integer> searchSexes = CommonTool.parseList(user.getSearchSex() != null && !user.getSearchSex().isEmpty() ? user.getSearchSex().replace("'", "").split(",") : new String[]{}, item -> Integer.valueOf(item));
        result.getSearchSexes().setValue(searchSexes.stream().reduce(Integer::sum).orElse(0));
        result.getSearchSexes().setTag(userService.parseSexes(searchSexes));
        List<Integer> searchCommunicate = CommonTool.parseList(user.getSearchCommunicate() != null && !user.getSearchCommunicate().isEmpty() ? user.getSearchCommunicate().replace("'", "").split(",") : new String[]{}, item -> Integer.valueOf(item));
        result.getSearchCommunicates().setValue(searchCommunicate.stream().reduce(Integer::sum).orElse(0));
        result.getSearchCommunicates().setTag(userService.parseCommunicates(searchCommunicate));
        result.setStartAge(user.getMatchStartAge());
        result.setEndAge(user.getMatchEndAge());
        result.setUseStatus(user.getUseStatus());
        result.setSearchStatus(user.getSearchStatus());
        result.setCommentStatus(user.getCommentStatus());
        result.setIsPenuser(user.getIsPenuser());
        result.setImages(userService.getUserImageList(user.getId(), BiuUserImageEntity.USE_TYPE_INTRODUCE));
        result.setDesc(userService.getUserDesc(user));
        return new FuncResult(true, "", result);
    }

    /**
     * 获取用户首页信息
     * @param userId
     * @param guestId
     * @return
     */
    public FuncResult getUserHomeInfo(long userId, long guestId) {
        BiuUserViewEntity user = userService.getUserView(userId);
        if (user == null) {
            return new FuncResult(false, "");
        }
        int hide = 0;
        if (guestId > 0 && guestId != userId && user.getSearchStatus() == BiuUserViewEntity.SEARCH_CLOSE_STATUS) {
            hide = 1;
        }
        if (guestId == 0 && user.getSearchStatus() == BiuUserViewEntity.SEARCH_CLOSE_STATUS) {
            hide = 1;
        }
        UserHomeResult result = new UserHomeResult();
        if (guestId > 0 && guestId == userId) {
            result.setSelf(1);
        }
        result.setImage(userService.parseImage(user.getImage()));
        if (guestId > 0 && result.getSelf() == 0) {
            ProviderOption option = new ProviderOption();
            option.addCondition("user_id", guestId);
            option.addCondition("relate_id", userId);
            BiuUserCollectEntity collect = biuDbFactory.getUserDbFactory().getBiuUserCollectImpl().single(option);
            if (collect == null) {
                result.setAllowCollect(1);
                result.setCollectTag("关注");
            } else {
                result.setCollect(1);
                result.setCollectTag("取消关注");
            }
            option = new ProviderOption();
            option.addCondition("user_id", guestId);
            option.addCondition("black_id", userId);
            BiuUserBlacklistEntity black = biuDbFactory.getUserDbFactory().getBiuUserBlacklistImpl().single(option);
            result.setBlack(black == null ? 0 : 1);
            BiuUserFriendEntity passed = userService.getUserFriend(guestId, userId, BiuUserFriendEntity.PASS_STATUS);
            if (passed != null) {
                result.setFriend(1);
                result.setFriendTag("解除好友");
                result.setCancelFriend(1);
                hide = 0;
            } else {
                BiuUserFriendEntity waiting = userService.getUserFriend(guestId, userId, BiuUserFriendEntity.WAITING_STATUS);
                if (waiting != null) {
                    result.setFriend(2);
                    result.setFriendTag("申请中");
                    result.setFriendHash(encodeHash(userId));
                    // 是否允许进行好友审核操作
                    result.setAllowOperateApply(userService.allowAuthFriend(waiting, guestId) ? 1 : 0);
                    hide = 0;
                } else if(user.getSearchStatus() == BiuUserViewEntity.SEARCH_OPEN_STATUS) {
                    result.setAllowFriend(1);
                    result.setFriendTag("申请好友");
                }
            }
        }
        result.setId(encodeHash(user.getId()));
        result.setTitle(user.getTitle());
        result.setIntroduce(user.getIntroduce());
        if (hide > 0) {
            result.setName(userService.createRandomNickName());
            result.setDesc("保密");
            result.setProvince("保密");
            result.setSex("保密");
            result.setAge("保密");
        } else {
            result.setName(user.getPenName());
            List<String> desc = new ArrayList<>();
            if (!areaService.getArea(user.getProvince()).isEmpty()) {
                result.setProvince(areaService.getArea(user.getProvince()));
                desc.add(result.getProvince());
            }
            if (!user.getSexTag().isEmpty()) {
                result.setSex(user.getSexTag());
                desc.add(result.getSex());
            }
            if (user.getAge() > 0) {
                result.setAge(user.getAge() + "岁");
                desc.add(result.getAge());
            }
            result.setDesc(String.join("/", desc));
        }
        List<UserInterestResult> interests = userService.getUserInterestList(user.getId());
        interests = interests.stream().filter(item -> {
            if (item.getChecked() > 0) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        result.setInterests(interests);
        List<Integer> communicates = CommonTool.parseList(user.getSelfCommunicate() != null && !user.getSelfCommunicate().isEmpty() ? user.getSelfCommunicate().replace("'", "").split(",") : new String[]{}, item -> Integer.valueOf(item));
        result.getCommunicate().setValue(communicates.stream().reduce(Integer::sum).orElse(0));
        result.getCommunicate().setTag(userService.parseCommunicates(communicates));
        result.setImages(userService.getUserImageList(user.getId(), BiuUserImageEntity.USE_TYPE_INTRODUCE, 0));
        return new FuncResult(true, "", result);
    }

    /**
     * 处理用户状态
     * @param userId
     * @param type
     * @param status
     * @return
     */
    public FuncResult processUserStatus(long userId, String type, int status) {
        BiuUserEntity user = userService.find(userId);
        if (user == null) {
            return new FuncResult(false, "用户信息不存在");
        }
        int statusValue;
        switch (type) {
            case "comment":
                statusValue = user.getCommentStatus();
                break;
            case "search":
                statusValue = user.getSearchStatus();
                break;
            default:
                statusValue = 0;
        }
        if (status == statusValue) {
            return new FuncResult(false, statusValue == 0 ? "已关闭" : "已开启");
        }
        switch (type) {
            case "comment":
                user.setCommentStatus(status);
                break;
            case "search":
                user.setSearchStatus(status);
                break;
            default:
                ;
        }
        biuDbFactory.getUserDbFactory().getBiuUserImpl().update(user);
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        return new FuncResult(true, "", result);
    }

    public FuncResult updateInfo(long userId, UserInfoBean bean) {
        BiuUserEntity user = userService.find(userId);
        if (user == null) {
            return new FuncResult(false, "用户信息不存在");
        }
        boolean sendNote = user.getIsPenuser() == BiuUserEntity.USER_NOT_PEN ? true : false;
        user.setSearchStatus(BiuUserEntity.SEARCH_OPEN_STATUS);
        if (bean.getMethod().contains("nick")) {
            user.setNick(bean.getNick());
        }
        if (bean.getMethod().contains("image")) {
            String imageUrl = bean.getImage().isEmpty() ? SystemOption.USER_IMAGE.getValue() : bean.getImage();
            BiuUserImageEntity image = userService.setUserImage(userId, BiuUserImageEntity.USE_TYPE_AVATOR, 0, imageUrl, 0);
            user.setImage(image.getFile());
        }
        if (bean.getMethod().contains("pen_name")) {
            if (!bean.getPenName().isEmpty() && !keywordService.verifyKeyword(bean.getPenName())) {
                return new FuncResult(false, "请查看是否有敏感词");
            }
            user.setPenName(bean.getPenName().trim());
        }
        if (bean.getMethod().contains("sex")) {
            user.setSex(bean.getSex() > 2 ? 0 : bean.getSex());
        }
        if (bean.getMethod().contains("birthday_year")) {
            user.setBirthdayYear(bean.getBirthdayYear());
        }
        if (bean.getMethod().contains("province")) {
            user.setProvince(bean.getProvince());
        }
        if (bean.getMethod().contains("username")) {
            if (!bean.getUsername().isEmpty() && !keywordService.verifyKeyword(bean.getUsername())) {
                return new FuncResult(false, "请查看是否有敏感词");
            }
            user.setUsername(bean.getUsername());
        }
        if (bean.getMethod().contains("phone")) {
            user.setPhone(bean.getPhone());
        }
        if (bean.getMethod().contains("city")) {
            user.setCity(bean.getCity());
        }
        if (bean.getMethod().contains("country")) {
            user.setCountry(bean.getCountry());
        }
        if (bean.getMethod().contains("address")) {
            user.setAddress(bean.getAddress());
        }
        if (bean.getMethod().contains("email")) {
            user.setEmail(bean.getEmail());
        }
        if (bean.getMethod().contains("zipcode")) {
            user.setZipcode(bean.getZipcode());
        }
        if (bean.getMethod().contains("title")) {
            if (!bean.getTitle().isEmpty() && !keywordService.verifyKeyword(bean.getTitle())) {
                return new FuncResult(false, "请查看是否有敏感词");
            }
            user.setTitle(bean.getTitle());
        }
        if (bean.getMethod().contains("introduce")) {
            if (!bean.getIntroduce().isEmpty() && !keywordService.verifyKeyword(bean.getIntroduce())) {
                return new FuncResult(false, "请查看是否有敏感词");
            }
            user.setIntroduce(bean.getIntroduce());
        }
        if (bean.getMethod().contains("match_start_age")) {
            user.setMatchStartAge(bean.getStartAge());
        }
        if (bean.getMethod().contains("match_end_age")) {
            user.setMatchEndAge(bean.getEndAge());
        }
        user.setIsPenuser(BiuUserEntity.USER_IS_PEN);
        biuDbFactory.getUserDbFactory().getBiuUserImpl().update(user);
        if (bean.getMethod().contains("images")) {
            userService.setUserImage(userId, BiuUserImageEntity.USE_TYPE_INTRODUCE, 0, bean.getImages());
        }
        if (bean.getMethod().contains("interest")) {
            userService.setUserInterest(userId, bean.getInterests());
        }
        if (bean.getMethod().contains("search_sex")) {
            userService.setUserSexes(userId, bean.getSearch_sexes());
        }
        if (bean.getMethod().contains("communicate")) {
            userService.setUserCommunicate(userId, BiuUserCommunicateEntity.USE_TYPE_SELF, bean.getCommunicates());
        }
        userService.setUserSortTime(user.getId());
        if (bean.getMethod().contains("search_communicate")) {
            userService.setUserCommunicate(userId, BiuUserCommunicateEntity.USE_TYPE_SEARCH, bean.getSearch_communicates());
        }
        if (sendNote) {
            userService.addUserMessage(0, user.getId(), BiuMessageEntity.PUBLIC_NOTICE, 0, "欢迎加入BIU笔友", "", SystemOption.REGISTER_NOTICE_BANNER.getValue(), Arrays.asList(SystemOption.REGISTER_NOTICE_IMAGE.getValue()).stream().collect(Collectors.toList()));
            userService.addUserMessage(0, user.getId(), BiuMessageEntity.PUBLIC_NOTICE, 0, "平台注意事项", "", SystemOption.REGISTER_NOTICE_PLAT_BANNER.getValue(), Arrays.asList(SystemOption.REGISTER_NOTICE_PLAT_IMAGE.getValue()).stream().collect(Collectors.toList()));
        }
        return new FuncResult(true);
    }

    public FuncResult submitReport(int type, long userId, long relateId, String content, List<String> images) {
        BiuUserEntity user = userService.find(userId);
        if (user == null) {
            return new FuncResult(false, "用户信息不存在");
        }
        BiuUserReportEntity entity = new BiuUserReportEntity();
        entity.setReportType(type);
        entity.setUserId(userId);
        entity.setRelateId(relateId);
        entity.setContent(content);
        biuDbFactory.getUserDbFactory().getBiuUserReportImpl().insert(entity);
//        if (type == BiuUserReportEntity.REPORT_TYPE_REPORT) {
//            blackUser(userId, relateId);
//        }
        if (images != null && !images.isEmpty()) {
            int imageType = 0;
            if (type == BiuUserReportEntity.REPORT_TYPE_TOUSU) {
                imageType = BiuUserImageEntity.USE_TYPE_TOUSU;
            } else if (type == BiuUserReportEntity.REPORT_TYPE_RECOMMEND) {
                imageType = BiuUserImageEntity.USE_TYPE_RECOMMEND;
            } else if (type == BiuUserReportEntity.REPORT_TYPE_OTHER) {
                imageType = BiuUserImageEntity.USE_TYPE_REPORT_OTHER;
            } else if (type == BiuUserReportEntity.REPORT_TYPE_REPORT) {
                imageType = BiuUserImageEntity.USE_TYPE_REPORT;
            }
            if (imageType > 0) {
                userService.setUserImage(userId, imageType, entity.getId(), images);
            }
        }
        return new FuncResult(true);
    }

    public void blackUser(long userId, long relateId) {
        BiuUserBlacklistEntity black = userService.getUserBlack(userId, relateId);
        if (black == null) {
            black = new BiuUserBlacklistEntity();
            black.setUserId(userId);
            black.setBlackId(relateId);
            biuDbFactory.getUserDbFactory().getBiuUserBlacklistImpl().insert(black);
        }
    }

    public void cancelBlackUser(long userId, long relateId) {
        BiuUserBlacklistEntity black = userService.getUserBlack(userId, relateId);
        if (black != null) {
            biuDbFactory.getUserDbFactory().getBiuUserBlacklistImpl().delete(black);
        }
    }

    public String encodeHash(long number) {
        return hashTool.getHashids(4).encode(number);
    }

    public long decodeHash(String hash) {
        return hashTool.getHashids(4).first(hash);
    }

    public FuncResult collect(long userId, long relateId, String method) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("relate_id", relateId);
        BiuUserCollectEntity collect = biuDbFactory.getUserDbFactory().getBiuUserCollectImpl().single(option);
        if (method.equals(UserCollectBean.COLLECT)) {
            if (collect != null) {
                return new FuncResult(false, "已关注");
            }
            userCollectService.pushUserCollect(userId, relateId, new Date());
            return new FuncResult(true);
        } else if(method.equals(UserCollectBean.CANCEL)) {
            if (collect == null) {
                return new FuncResult(false, "未关注");
            }
            userCollectService.cancelUserCollect(userId, relateId, new Date());
            return new FuncResult(true);
        }
        return new FuncResult(false, "操作错误");
    }

    /**
     * 处理笔友申请
     * @param userId
     * @param friendId
     * @param bean
     * @return
     */
    public FuncResult processFriend(long userId, long friendId, ApplyFriendBean bean) {
        String method = bean.getMethod();
        if (userId == friendId) {
            return new FuncResult(false, "不能是同一人");
        }
        if (method.equals(ApplyFriendBean.APPLY)) {
            userService.applyFriend(userId, friendId, bean.getCommunicate());
            return new FuncResult(true);
        } else if(method.equals(ApplyFriendBean.COMMUNICATE)) {
            List<FriendCommunicateInfo> list = userService.getFriendCommunicateList(Arrays.asList(userId, friendId));
            return new FuncResult(true, "", list);
        } else if(method.equals(ApplyFriendBean.PASS)) {
            JsonResult result = userService.passFriend(userId, friendId);
            if (result.getCode() == ResponseConfig.SUCCESS_CODE) {
                return new FuncResult(true);
            }
            return new FuncResult(false, result.getMessage());
        } else if(method.equals(ApplyFriendBean.REFUSE)) {
            JsonResult result = userService.refuseFriend(userId, friendId);
            if (result.getCode() == ResponseConfig.SUCCESS_CODE) {
                return new FuncResult(true);
            }
            return new FuncResult(false, result.getMessage());
        } else if(method.equals(ApplyFriendBean.CANCEL)) {
            JsonResult result = userService.removeFriend(userId, friendId);
            if (result.getCode() == ResponseConfig.SUCCESS_CODE) {
                return new FuncResult(true);
            }
            return new FuncResult(false, result.getMessage());
        }
        return new FuncResult(false, "操作类型有误");
    }

    /**
     * 关注用户列表
     * @param userId
     * @return
     */
    public FuncResult getCollectList(long userId) {
        List<CollectUserResult> list = new ArrayList<>();
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addOrderby("created_at desc");
        List<BiuUserCollectEntity> rows = biuDbFactory.getUserDbFactory().getBiuUserCollectImpl().list(option);
        if (rows == null) {
            return new FuncResult(false, "无对应记录", list);
        }
        rows.forEach(item -> {
            BiuUserViewEntity entity = userService.getUserView(item.getRelateId());
            if (entity != null && entity.getUseStatus() == BiuUserViewEntity.USER_AVAIL_STATUS) {
                int search = entity.getSearchStatus();
                CollectUserResult unit = new CollectUserResult();
                unit.setId(encodeHash(entity.getId()));
                unit.setImage(userService.parseImage(entity.getImage()));
                if (search == BiuUserViewEntity.SEARCH_CLOSE_STATUS) {
                    unit.setName(userService.createRandomNickName());
                    unit.setDesc("隐藏");
                } else {
                    int communicate = 0;
                    if (entity.getSelfCommunicate() != null && !entity.getSelfCommunicate().isEmpty()) {
                        communicate = Arrays.asList(entity.getSelfCommunicate().trim().replaceAll("'", "").split(",")).stream().map(value -> Integer.parseInt(value)).reduce(Integer::sum).orElse(0);
                    }
                    unit.setName(entity.getPenName());
                    unit.setDesc(userService.getUserDesc(entity));
                    unit.setCommunicate(communicate);
                }
                list.add(unit);
            }
        });
        return new FuncResult(true, "", list);
    }

    /**
     * 关注用户列表
     * @param userId
     * @return
     */
    public FuncResult getBlackList(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addOrderby("created_at desc");
        List<BiuUserBlacklistEntity> rows = biuDbFactory.getUserDbFactory().getBiuUserBlacklistImpl().list(option);
        List<BlackUserResult> list = new ArrayList<>();
        if (rows == null) {
            return new FuncResult(false, "无对应记录", list);
        }
        rows.forEach(item -> {
            BiuUserViewEntity entity = userService.getUserView(item.getBlackId());
            if (entity != null && entity.getUseStatus() == BiuUserViewEntity.USER_AVAIL_STATUS) {
                int search = entity.getSearchStatus();
                BlackUserResult unit = new BlackUserResult();
                unit.setId(encodeHash(entity.getId()));
                unit.setImage(userService.parseImage(entity.getImage()));
                if (search == BiuUserViewEntity.SEARCH_CLOSE_STATUS) {
                    unit.setName(userService.createRandomNickName());
                    unit.setDesc("隐藏");
                } else {
                    int communicate = 0;
                    if (entity.getSelfCommunicate() != null && !entity.getSelfCommunicate().isEmpty()) {
                        communicate = Arrays.asList(entity.getSelfCommunicate().trim().replaceAll("'", "").split(",")).stream().map(value -> Integer.parseInt(value)).reduce(Integer::sum).orElse(0);
                    }
                    unit.setName(entity.getPenName());
                    unit.setDesc(userService.getUserDesc(entity));
                    unit.setCommunicate(communicate);
                }
                list.add(unit);
            }
        });
        return new FuncResult(true, "", list);
    }

    /**
     * 获取笔友列表
     * @param userId
     * @return
     */
    public FuncResult getUserFriendList(long userId) {
        List<UserFriendResult> list = new ArrayList<>();
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("confirm_status", BiuUserFriendMemberEntity.PASS_STATUS);
        List<BiuUserFriendMemberEntity> members = biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().list(option);
        if (members == null) {
            return new FuncResult(false, "无对应记录", list);
        }
        if (members.isEmpty()) {
            return new FuncResult(false, "无对应记录", list);
        }
        String friendIdList = members.stream().map(item -> String.valueOf(item.getFriendId())).collect(Collectors.joining(","));
        option = new ProviderOption();
        option.addCondition("id in (" + friendIdList + ")");
        option.addCondition("confirm_status", BiuUserFriendEntity.PASS_STATUS);
        List<BiuUserFriendEntity> friends = biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().list(option);
        if (friends.isEmpty()) {
            return new FuncResult(false, "无对应记录", new ArrayList<UserFriendResult>());
        }
        list = userService.processFriendList(friends, userId);
        return new FuncResult(true, "", list);
    }

    /**
     * 获取好友邮寄历史列表
     * @param id
     * @param userId
     * @return
     */
    public FuncResult getUserFriendCommunicateLogList(long id, long userId) {
        List<Map> list = new ArrayList<>();
        BiuUserFriendEntity friend = biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().find(id);
        if (friend == null) {
            return new FuncResult(false, "无对应记录", list);
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("friend_id", friend.getId());
        option.addOrderby("id asc");
        List<BiuUserFriendCommunicateLogEntity> logs = biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().list(option);
        list = userService.processFriendCommunicateLogs(friend, logs, userId);
        return new FuncResult(true, "", list);
    }

    /**
     * 处理邮件发收
     * @param userId
     * @param bean
     * @return
     */
    public FuncResult processSignCommunicate(long userId, SignCommunicateBean bean) {
        Map result = null;
        System.out.println("friend_id" + decodeHash(bean.getFriend()));
        BiuUserFriendEntity friend = biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().find(decodeHash(bean.getFriend()));
        if (friend == null) {
            return new FuncResult(false, "无对应记录");
        }
        if (friend.getConfirmStatus() != BiuUserFriendEntity.PASS_STATUS) {
            return new FuncResult(false, "好友记录无效");
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("DATE_FORMAT(created_at,'%Y-%m-%d')='" + TimeTool.formatDate(new Date(), TimeFormat.DEFAULT_DATE.getValue()) + "'");
        option.addCondition("friend_id", friend.getId());
        if (bean.getMethod().equals(SignCommunicateBean.SEND)) {
            option.addCondition("send_user", userId);
            option.addCondition("receive_status", BiuUserFriendCommunicateLogEntity.RECEIVING);
            BiuUserFriendCommunicateLogEntity todayLog = biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().single(option);
            if (todayLog != null) {
                return new FuncResult(false, "今天您已寄出");
            }
            result = userService.sendFriendCommunicate(friend, userId);
        } else {
            option.addCondition("receive_user", userId);
            option.addCondition("receive_status", BiuUserFriendCommunicateLogEntity.RECEIVED);
            BiuUserFriendCommunicateLogEntity todayLog = biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().single(option);
            if (todayLog != null) {
                return new FuncResult(false, "今天您已接收");
            }
            result = userService.receiveFriendCommunicate(friend, userId);
        }
        return new FuncResult(true, "", result);
    }

    /**
     * 待读用户消息数
     * @param userId
     * @return
     */
    public FuncResult readingMessageCount(long userId) {
        Map<String, Long> result = new HashMap<>();
        ProviderOption option = new ProviderOption();
        option.addCondition("dest_id", userId);
        String types = Arrays.asList(BiuMessageEntity.NOTICE_APPLY, BiuMessageEntity.NOTICE_FRIEND, BiuMessageEntity.NOTICE_SEND, BiuMessageEntity.NOTICE_RECEIVE).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(","));
        option.addCondition("message_type in (" + types + ")");
        option.addCondition("read_status", BiuMessageEntity.READ_WAITING);
        long notice_count = biuDbFactory.getUserDbFactory().getBiuMessageViewImpl().count(option);
        result.put("notice", notice_count);
        option = new ProviderOption();
        option.addCondition("dest_id", userId);
        types = Arrays.asList(BiuMessageEntity.MESSAGE_COMMENT, BiuMessageEntity.MESSAGE_FAVOR, BiuMessageEntity.MESSAGE_REPLY).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(","));
        option.addCondition("message_type in (" + types + ")");
        option.addCondition("read_status", BiuMessageEntity.READ_WAITING);
        long message_count = biuDbFactory.getUserDbFactory().getBiuMessageViewImpl().count(option);
        result.put("message", message_count);
        option = new ProviderOption();
        option.addCondition("dest_id", userId);
        types = Arrays.asList(BiuMessageEntity.PUBLIC_NOTICE, BiuMessageEntity.PUBLIC_ACTIVE, BiuMessageEntity.PUBLIC_UPDATE).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(","));
        option.addCondition("message_type in (" + types + ")");
        option.addCondition("read_status", BiuMessageEntity.READ_WAITING);
        long public_count = biuDbFactory.getUserDbFactory().getBiuMessageViewImpl().count(option);
        result.put("public", public_count);
        return new FuncResult(true, "", result);
    }

    /**
     * 用户信息列表
     * @param userId
     * @param bean
     * @return
     */
    public FuncResult messageList(long userId, UserMessageListBean bean) {
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
        result.put("more", 0);
        List<UserMessageResult> messageList = new ArrayList<>();
        result.put("list", messageList);
        result.put("last", "");
        result.put("tip", readingMessageCount(userId));
        ProviderOption option = new ProviderOption();
        option.addCondition("dest_id", userId);
        if (bean.getRead() < 2) {
            option.addCondition("read_status", bean.getRead());
        }
        if (bean.getSub() > 0) {
            option.addCondition("message_type", bean.getSub());
        } else {
            option.addCondition("message_type in (" + bean.subList().stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
        }
        option.addOrderby("created_at desc");
        option.setUsePager(true);
        option.setOffset(bean.getOffset());
        option.setLimit(bean.getSize() + 1);
        List<BiuMessageViewEntity> rows = biuDbFactory.getUserDbFactory().getBiuMessageViewImpl().list(option);
        if (rows == null) {
            return new FuncResult(false, "无对应记录", result);
        }
        BiuUserViewEntity user = userService.getUserView(userId);
        if (rows.size() > bean.getSize()) {
            rows.remove(rows.size() - 1);
            result.put("more", 1);
        }
        if (rows.isEmpty()) {
            return new FuncResult(false, "无对应记录", result);
        }
        result.put("last", encodeHash(rows.get(rows.size() - 1).getId()));
        messageList = processMessageList(rows, user);
        result.put("list", messageList);
        return new FuncResult(true, "", result);
    }

    public List<UserMessageResult> processMessageList(List<BiuMessageViewEntity> rows, BiuUserViewEntity user) {
        List<UserMessageResult> list = new ArrayList<>();
        rows.forEach(item -> {
            UserMessageResult unit = new UserMessageResult();
            unit.setMessageType(item.getMessageType());
            unit.setMessageTag(item.getMessageTag());
            unit.setReadStatus(item.getReadStatus());
            unit.setId(encodeHash(item.getId()));
            unit.setTitle(item.getTitle());
            unit.setContent(item.getContent());
            unit.setShowTime(TimeTool.formatDate(item.getCreatedAt(), "yyyy/MM/dd HH:mm:ss"));
            ProviderOption option = new ProviderOption();
            option.addCondition("use_type", BiuUserImageEntity.USE_TYPE_MESSAGE);
            option.addCondition("relate_id", item.getId());
            option.addOrderby("sort_index asc");
            List<String> images = biuDbFactory.getUserDbFactory().getBiuUserImageImpl().list(option).
                    stream().map(imageItem -> userService.parseImage(imageItem.getFile())).collect(Collectors.toList());
            unit.setImages(images);
            List<String> banners = null;
            if (item.getMessageType() == BiuMessageEntity.PUBLIC_NOTICE || item.getMessageType() == BiuMessageEntity.PUBLIC_ACTIVE || item.getMessageType() == BiuMessageEntity.PUBLIC_UPDATE) {
                if (item.getBanner() != null && !item.getBanner().isEmpty()) {
                    banners = Arrays.asList(item.getBanner()).stream().collect(Collectors.toList());
                } else {
                    banners = new ArrayList<>();
                }
            } else {
                banners = images;
            }
            unit.setBanners(banners);
            if (item.getMessageType() == BiuMessageEntity.NOTICE_APPLY || item.getMessageType() == BiuMessageEntity.NOTICE_FRIEND) {
                long sourceId = item.getSourceId();
                long destId = item.getDestId();
                if (destId == sourceId) {
                    destId = item.getRelateId();
                }
                unit.setUseFriendApply(1);
                BiuUserFriendEntity friendEntity = userService.getUserFriend(item.getFriendId());
                if (friendEntity != null) {
                    long friendId = userService.getFriendId(friendEntity, user.getId());
                    BiuUserViewEntity friendUser = userService.getUserView(friendId);
                    if (friendUser != null) {
                        unit.getFriendApply().setId(encodeHash(friendEntity.getId()));
                        unit.getFriendApply().setUser(encodeHash(user.getId()));
                        unit.getFriendApply().setFriend(encodeHash(friendId));
                        unit.getFriendApply().setName(friendUser.getPenName());
                        unit.getFriendApply().setImage(userService.parseImage(friendUser.getImage()));
                        unit.getFriendApply().setDesc(userService.getUserDesc(friendUser));
                        if (userService.allowAuthFriend(friendEntity, user.getId()) && friendEntity.getConfirmStatus() == BiuUserFriendEntity.WAITING_STATUS) {
                            unit.getFriendApply().setAllowAuth(UserMessageFriendResult.ALLOW_AUTH);
                        }
                        if (friendEntity.getConfirmStatus() != BiuUserFriendEntity.REFUSE_STATUS) {
                            unit.getFriendApply().setStatus(UserMessageFriendResult.ENABLE);
                        }
                        if (friendEntity.getConfirmStatus() == BiuUserFriendEntity.PASS_STATUS) {
                            if (friendEntity.getCommunicateType() == BiuUserCommunicateEntity.COM_METHOD_LETTER) {
                                unit.getFriendApply().getInfo().add(new HashMap<String, Object>(){{ put("name", "通信方式"); put("value", BiuUserCommunicateEntity.LABEL_COM_METHOD_LETTER); }});
                                unit.getFriendApply().getInfo().add(new HashMap<String, Object>(){{ put("name", "收件人"); put("value", friendUser.getUsername()); }});
                                unit.getFriendApply().getInfo().add(new HashMap<String, Object>(){{ put("name", "电话"); put("value", friendUser.getPhone()); }});
                                unit.getFriendApply().getInfo().add(new HashMap<String, Object>(){{ put("name", "通信地址"); put("value", userService.getUserAddress(friendId)); }});
                            } else {
                                unit.getFriendApply().getInfo().add(new HashMap<String, Object>(){{ put("name", "通信方式"); put("value", BiuUserCommunicateEntity.LABEL_COM_METHOD_EMAIL); }});
                                unit.getFriendApply().getInfo().add(new HashMap<String, Object>(){{ put("name", "E-mail"); put("value", friendUser.getEmail()); }});
                            }
                        }
                        list.add(unit);
                    }
                } else {
                    unit.getFriendApply().setIsCancel(1);
                    unit.getFriendApply().setStatus(UserMessageFriendResult.UNENABLE);
                }
            } else if(item.getMessageType() == BiuMessageEntity.MESSAGE_FAVOR || item.getMessageType() == BiuMessageEntity.MESSAGE_COMMENT || item.getMessageType() == BiuMessageEntity.MESSAGE_REPLY) {
                if (item.getRelateType() == BiuMessageEntity.RELATE_NOTE_TYPE) {
                    unit.setNote(encodeHash(item.getRelateId()));
                    list.add(unit);
                } else {
                    BiuHoleNoteCommentEntity comment = biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().find(item.getRelateId());
                    if (comment != null) {
                        unit.setNote(encodeHash(comment.getNoteId()));
                        list.add(unit);
                    }
                }
            } else {
                list.add(unit);
            }
        });
        return list;
    }

    /**
     * 标记消息已读
     * @param userId
     * @param messageId
     * @return
     */
    public FuncResult readMessage(long userId, long messageId) {
        BiuMessageEntity message = biuDbFactory.getUserDbFactory().getBiuMessageImpl().find(messageId);
        if (message == null) {
            return new FuncResult(false, "用户信息不存在");
        }
        if (message.getDestId() != userId) {
            return new FuncResult(false, "非本用户消息");
        }
        if (message.getReadStatus() == BiuMessageEntity.READ_OK) {
            return new FuncResult(false, "已标记已读");
        }
        message.setReadStatus(BiuMessageEntity.READ_OK);
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().update(message);
        return new FuncResult(true);
    }

    /**
     * 删除消息
     * @param userId
     * @param messageId
     * @return
     */
    public FuncResult removeMessage(long userId, long messageId) {
        BiuMessageEntity message = biuDbFactory.getUserDbFactory().getBiuMessageImpl().find(messageId);
        if (message == null) {
            return new FuncResult(false, "用户信息不存在");
        }
        if (message.getDestId() != userId) {
            return new FuncResult(false, "非本用户消息");
        }
        biuDbFactory.getUserDbFactory().getBiuMessageImpl().delete(message);
        return new FuncResult(true);
    }

    public FuncResult getNoteInitSelection(long userId) {
        Map result = userService.getNoteInitSelection(userId);
        return new FuncResult(true, "", result);
    }

    /**
     * 生成树洞记录
     * @param userId
     * @param bean
     * @return
     */
    public FuncResult createHoleNote(long userId, CreateNoteBean bean) {
        BiuHoleNoteEntity note = new BiuHoleNoteEntity();
        note.setUserId(userId);
        note.setContent(bean.getContent());
        note.setMoodCode(bean.getMood());
        note.setLabelId(bean.getLabel());
        note.setIsPrivate(bean.getIsSelfRealValue());
        note.setNickShow(bean.getNickRealValue());
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().insert(note);
        if (note.getId() > 0) {
            userService.setUserImage(userId, BiuUserImageEntity.USE_TYPE_NOTE, note.getId(), bean.getImages());
        }
        return new FuncResult(true, "", new HashMap<>());
    }

    /**
     * 编辑树洞记录
     * @param userId
     * @param bean
     * @return
     */
    public FuncResult editHoleNote(long userId, CreateNoteBean bean) {
        long noteId = decodeHash(bean.getId());
        if (noteId <= 0) {
            return new FuncResult(false, "无对应记录");
        }
        BiuHoleNoteEntity note = biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().find(noteId);
        if (note == null) {
            return new FuncResult(false, "无对应记录");
        }
        if (note.getUserId() != userId) {
            return new FuncResult(false, "非本人树洞消息，不可修改");
        }
        note.setContent(bean.getContent());
        note.setMoodCode(bean.getMood());
        note.setLabelId(bean.getLabel());
        note.setIsPrivate(bean.getIsSelfRealValue());
        note.setNickShow(bean.getNickRealValue());
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().update(note);
        if (note.getId() > 0) {
            userService.setUserImage(userId, BiuUserImageEntity.USE_TYPE_NOTE, note.getId(), bean.getImages());
        }
        return new FuncResult(true, "");
    }

    /**
     * 删除树洞消息
     * @param userId
     * @param bean
     * @return
     */
    public FuncResult removeHoleNote(long userId, RemoveNoteBean bean) {
        long noteId = decodeHash(bean.getId());
        if (noteId <= 0) {
            return new FuncResult(false, "无对应记录");
        }
        BiuHoleNoteEntity note = biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().find(noteId);
        if (note == null) {
            return new FuncResult(false, "无对应记录");
        }
        if (note.getUserId() != userId) {
            return new FuncResult(false, "非本人树洞消息，不可修改");
        }
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().delete(note);
        return new FuncResult(true, "");
    }

    public FuncResult getNoteList(long userId, NoteListBean bean) {
        BiuUserViewEntity user = userService.getUserView(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
        result.put("more", 0);
        result.put("list", new ArrayList<Map>());
        result.put("last", "");
        if (!bean.getMethod().equals(NoteListBean.INDEX) && userId <= 0) {
            return new FuncResult(false, "无对应记录", result);
        }
        ProviderOption option = new ProviderOption();
        if (bean.getMethod().equals(NoteListBean.INDEX)) {
            option.addCondition("is_private", BiuHoleNoteEntity.PRIVATE_NO);
        } else if(bean.getMethod().equals(NoteListBean.MINE)) {
            option.addCondition("user_id", userId);
        } else if(bean.getMethod().equals(NoteListBean.FRIEND)) {
            long friend = decodeHash(bean.getFriend());
            option.addCondition("user_id", friend);
            if (userId != friend) {
                option.addCondition("nick_show", BiuHoleNoteEntity.NICK_NO);
                option.addCondition("is_private", BiuHoleNoteEntity.PRIVATE_NO);
            }
        } else {
            result.put("list", new ArrayList<>());
            return new FuncResult(false, "无对应记录", result);
        }
        if (!bean.getLast().isEmpty()) {
            long last = decodeHash(bean.getLast());
            option.addCondition("id<" + last);
        }
        if (user != null && user.getProtectedUser() != null && !user.getProtectedUser().isEmpty()) {
            option.addCondition("user_id not in (" + user.getProtectedUser().replaceAll("'", "") + ")");
        }

        option.setUsePager(true);
        option.addOrderby("id desc");
        option.setOffset(bean.getPage(), bean.getSize());
        option.setLimit(bean.getSize() + 1);
        BiuHoleNoteViewImpl viewImpl = biuDbFactory.getHoleDbFactory().getHoleNoteViewImpl();
        List<BiuHoleNoteViewEntity> rows = viewImpl.list(option);
        if (rows.size() > bean.getSize()) {
            rows.remove(rows.size() - 1);
            result.put("more", 1);
        }
        if (rows.isEmpty()) {
            return new FuncResult(false, "无对应记录", result);
        }
        result.put("last", encodeHash(rows.get(rows.size() - 1).getId()));
        List<Map> list = processNoteList(rows, userId);
        result.put("list", list);
        return new FuncResult(true, "", result);
    }

    public List<Map> processNoteList(List<BiuHoleNoteViewEntity> rows, long userId) {
        List<Map> list = new ArrayList<>();
        BiuUserImpl userImpl = biuDbFactory.getUserDbFactory().getBiuUserImpl();
        BiuUserEntity user = userImpl.find(userId);
        rows.forEach(item -> {
            BiuUserEntity noteUser = null;
            if (item.getUserId() == userId) {
                noteUser = user;
            } else {
                noteUser = userImpl.find(item.getUserId());
            }
            BiuMoodEntity moodEntity = null;
            BiuLabelEntity labelEntity = null;
            if (!item.getMoodCode().isEmpty()) {
                ProviderOption option = new ProviderOption();
                option.addCondition("code", item.getMoodCode());
                moodEntity = biuDbFactory.getHoleDbFactory().getMoodImpl().single(option);
            }
            if (item.getLabelId() > 0) {
                labelEntity = biuDbFactory.getHoleDbFactory().getLabelImpl().find(item.getLabelId());
            }
            Map<String, Object> unit = new HashMap<>();
            unit.put("id", encodeHash(item.getId()));
            unit.put("user", encodeHash(noteUser.getId()));
            if (item.getNickShow() == BiuHoleNoteEntity.NICK_YES) {
//            if (((user != null && user.getId() != noteUser.getId()) || user == null) && item.getNickShow() == BiuHoleNoteEntity.NICK_YES) {
                unit.put("name", userService.createRandomNickName());
                unit.put("image", userService.parseImage(SystemOption.USER_IMAGE.getValue()));
            } else {
                unit.put("name", noteUser.getPenName());
                unit.put("image", userService.parseImage(noteUser.getImage()));
            }
            unit.put("mood_code", item.getMoodCode());
            unit.put("mood_emoj", moodEntity != null ? moodEntity.getEmoj() : "");
            unit.put("mood_tag", moodEntity != null ? moodEntity.getTag() : "");
            unit.put("label", labelEntity != null ? labelEntity.getId() : 0);
            unit.put("label_tag", labelEntity != null ? labelEntity.getTag() : "");
            unit.put("content", item.getContent());
            unit.put("favor_num", item.getFavorNum());
            unit.put("comment_num", item.getCommentNum());
            unit.put("create_time", TimeTool.friendlyTime(item.getCreatedAt()));
            unit.put("list_time", TimeTool.formatDate(item.getCreatedAt(), "MM/dd"));
            if (user != null) {
                unit.put("is_favor", userService.isFavored(user.getId(), item.getId()) ? 1 : 0);
                unit.put("is_commented", userService.isCommented(user.getId(), item.getId()) ? 1 : 0);
            } else {
                unit.put("is_favor", 0);
                unit.put("is_commented", 0);
            }
            unit.put("is_collect", 0);
            unit.put("allow_report", 0);
            unit.put("allow_remove", 0);
            unit.put("is_private", item.getIsPrivate() > 0 ? BiuHoleNoteEntity.PRIVATE_NO : BiuHoleNoteEntity.PRIVATE_YES);
            unit.put("nick_show", item.getRealShow());
            if (noteUser.getCommentStatus() == BiuUserEntity.COMMUNICATE_OPEN_STATUS) {
                if (user != null && user.getId() != noteUser.getId()) {
                    unit.put("allow_comment", 1);
                }
            } else {
                unit.put("allow_comment", 0);
            }
            if (user != null) {
                if (user.getId() != noteUser.getId()) {
                    unit.put("is_collect", userCollectService.isCollected(user.getId(), noteUser.getId()) ? 1 : 0);
                    unit.put("allow_report", 1);
                } else {
                    unit.put("allow_remove", 1);
                }
            }
            Map favorResult = userService.getNoteFavorCondition(item.getId());
            unit.put("favor_images", favorResult.get("images"));
            unit.put("images", userService.getNoteImages(item.getId(), BiuUserImageEntity.USE_TYPE_NOTE, 0));
            list.add(unit);
        });
        return list;
    }

    public FuncResult noteInfo(long userId, NoteInfoBean bean) {
        Map<String, Object> result = new HashMap<>();
        long id = decodeHash(bean.getId());
        BiuHoleNoteViewEntity note = biuDbFactory.getHoleDbFactory().getHoleNoteViewImpl().find(id);
        if (note == null) {
            return new FuncResult(false, "无对应记录");
        }
        BiuUserViewEntity user = userService.getUserView(userId);
        BiuUserViewEntity noteUser = userService.getUserView(note.getUserId());
        if (noteUser == null) {
            return new FuncResult(false, "树洞信已丢失");
        }
        BiuMoodEntity moodEntity = null;
        BiuLabelEntity labelEntity = null;
        if (!note.getMoodCode().isEmpty()) {
            ProviderOption option = new ProviderOption();
            option.addCondition("code", note.getMoodCode());
            moodEntity = biuDbFactory.getHoleDbFactory().getMoodImpl().single(option);
        }
        if (note.getLabelId() > 0) {
            labelEntity = biuDbFactory.getHoleDbFactory().getLabelImpl().find(note.getLabelId());
        }
        result.put("id", encodeHash(note.getId()));
        result.put("user", encodeHash(note.getUserId()));
        result.put("self", note.getUserId() == userId ? 1 : 0);
        if (((user != null && user.getId() != noteUser.getId()) || user == null) && note.getNickShow() == BiuHoleNoteEntity.NICK_YES) {
            result.put("name", userService.createRandomNickName());
            result.put("image", userService.parseImage(SystemOption.USER_IMAGE.getValue()));
        } else {
            result.put("name", noteUser.getPenName());
            result.put("image", userService.parseImage(noteUser.getImage()));
        }
        result.put("mood_code", note.getMoodCode());
        result.put("mood_emoj", moodEntity != null ? moodEntity.getEmoj() : "");
        result.put("mood_tag", moodEntity != null ? moodEntity.getTag() : "");
        result.put("label", labelEntity != null ? labelEntity.getId() : 0);
        result.put("label_tag", labelEntity != null ? labelEntity.getTag() : "");
        result.put("content", note.getContent());
        result.put("favor_num", note.getFavorNum());
        result.put("comment_num", note.getCommentNum());
        result.put("create_time", TimeTool.friendlyTime(note.getCreatedAt()));
        if (userId > 0) {
            result.put("is_favor", userService.isFavored(userId, note.getId()) ? 1 : 0);
            result.put("is_commented", userService.isCommented(userId, note.getId()) ? 1 : 0);
        } else {
            result.put("is_favor", 0);
            result.put("is_commented", 0);
        }
        result.put("is_collect", 0);
        result.put("allow_report", 0);
        result.put("allow_remove", 0);
        result.put("is_private", note.getIsPrivate() > 0 ? BiuHoleNoteEntity.PRIVATE_NO : BiuHoleNoteEntity.PRIVATE_YES);
        result.put("nick_show", note.getRealShow());
        if (noteUser.getCommentStatus() == BiuUserEntity.COMMUNICATE_OPEN_STATUS) {
            if (user != null && user.getId() != noteUser.getId()) {
                result.put("allow_comment", 1);
            }
        } else {
            result.put("allow_comment", 0);
        }
        if (user != null) {
            if (user.getId() != noteUser.getId()) {
                result.put("is_collect", userCollectService.isCollected(user.getId(), noteUser.getId()) ? 1 : 0);
                result.put("allow_report", 1);
            } else {
                result.put("allow_remove", 1);
            }
        }
        Map favorResult = userService.getNoteFavorCondition(note.getId());
        result.put("favor_images", favorResult.get("images"));
        result.put("images", userService.getNoteImages(note.getId(), BiuUserImageEntity.USE_TYPE_NOTE, 0));
        return new FuncResult(true, "", result);
    }

    /**
     * 点赞树洞信
     * @param userId
     * @param bean
     * @return
     */
    public FuncResult favorNote(long userId, NoteInfoBean bean) {
        Map<String, Object> result = new HashMap<>();
        long id = decodeHash(bean.getId());
        BiuHoleNoteEntity note = biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().find(id);
        if (note == null) {
            return new FuncResult(false, "无对应记录");
        }
        BiuUserViewEntity noteUser = userService.getUserView(note.getUserId());
        if (noteUser.getCommentStatus() == BiuUserEntity.COMMUNICATE_CLOSE_STATUS) {
            return new FuncResult(false, "该用户树洞信不可评论");
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("note_id", id);
        BiuUserFavorEntity favor = biuDbFactory.getHoleDbFactory().getBiuUserFavorImpl().single(option);
        if (favor == null) {
            favor = new BiuUserFavorEntity();
            favor.setUserId(userId);
            favor.setNoteId(id);
            favor.setRelateId(note.getUserId());
            biuDbFactory.getHoleDbFactory().getBiuUserFavorImpl().insert(favor);
            userService.addNoteFavorMessage(userId, note.getUserId(), id, BiuMessageEntity.RELATE_NOTE_TYPE);
        } else {
            biuDbFactory.getHoleDbFactory().getBiuUserFavorImpl().delete(favor);
        }
        Map favorResult = userService.getNoteFavorCondition(id);
        result.put("favor_num", favorResult.get("number"));
        result.put("images", favorResult.get("images"));
        return new FuncResult(true, "", result);
    }

    /**
     * 处理树洞标签
     * @param userId
     * @param bean
     * @return
     */
    public FuncResult processLabel(long userId, OperateLabelBean bean) {
        JsonDataResult<Map> result;
        if (bean.getMethod().equals(OperateLabelBean.ADD)) {
            result = userService.addLabel(userId, bean.getTag());
        } else if(bean.getId() > 0) {
            result = userService.removeLabel(userId, bean.getId());
        } else {
            result = new JsonDataResult<>("操作错误");
        }
        if (result.getCode() == ResponseConfig.SUCCESS_CODE) {
            return new FuncResult(true, "", result.getData());
        }
        return new FuncResult(false, result.getMessage());
    }

    /**
     * 处理树洞评论
     * @param userId
     * @param bean
     * @return
     */
    public FuncResult processNoteComment(long userId, NoteCommentBean bean) {
        JsonDataResult<Map> result = null;
        if (bean.getMethod().equals(NoteCommentBean.NOTE)) {
            long noteId = decodeHash(bean.getNote());
            result = commentNote(userId, noteId, bean.getContent());
        } else if(bean.getMethod().equals(NoteCommentBean.COMMENT)) {
            long commentId = decodeHash(bean.getComment());
            result = replyComment(userId, commentId, bean.getContent());
        }
        if (result == null) {
            return new FuncResult(false, "", "参数错误");
        }
        if (result.getCode() != ResponseConfig.SUCCESS_CODE) {
            return new FuncResult(false, "", result.getMessage());
        }
        return new FuncResult(true, "", result.getData());
    }

    /**
     * 直接评论树洞信
     * @param userId
     * @param noteId
     * @param content
     * @return
     */
    public JsonDataResult<Map> commentNote(long userId, long noteId, String content) {
        BiuHoleNoteEntity note = biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().find(noteId);
        if (note == null) {
            return new JsonDataResult<>("树洞信不见了");
        }
        BiuUserViewEntity noteUser = userService.getUserView(note.getUserId());
        if (noteUser.getCommentStatus() == BiuUserEntity.COMMUNICATE_CLOSE_STATUS) {
            return new JsonDataResult<>("该用户树洞信不可评论");
        }
        BiuHoleNoteCommentEntity entity = new BiuHoleNoteCommentEntity();
        entity.setUserId(userId);
        entity.setNoteId(noteId);
        entity.setContent(content);
        entity.setCommentUserid(note.getUserId());
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().insert(entity);
        if (userId != note.getUserId()) {
            userService.addNoteCommentMessage(userId, note.getUserId(), note.getId());
        }
        BiuHoleNoteViewEntity noteInfo = biuDbFactory.getHoleDbFactory().getHoleNoteViewImpl().find(noteId);
        Map<String, Object> result = new HashMap<>();
        result.put("comment_num", noteInfo.getCommentNum());
        return new JsonDataResult<>(ResponseConfig.SUCCESS_CODE, "", result);
    }

    /**
     * 回复树洞信评论
     * @param userId
     * @param commentId
     * @param content
     * @return
     */
    public JsonDataResult<Map> replyComment(long userId, long commentId, String content) {
        BiuHoleNoteCommentEntity comment = biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().find(commentId);
        if (comment == null) {
            return new JsonDataResult<>("树洞评论不见了");
        }
        BiuHoleNoteEntity note = biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().find(comment.getNoteId());
        if (note == null) {
            return new JsonDataResult<>("树洞信不见了");
        }
        BiuUserViewEntity noteUser = userService.getUserView(note.getUserId());
        if (noteUser.getCommentStatus() == BiuUserEntity.COMMUNICATE_CLOSE_STATUS) {
            return new JsonDataResult<>("该用户树洞信不可评论");
        }
        BiuHoleNoteCommentEntity entity = new BiuHoleNoteCommentEntity();
        entity.setUserId(userId);
        entity.setNoteId(comment.getNoteId());
        entity.setCommentId(commentId);
        entity.setContent(content);
        entity.setCommentUserid(comment.getUserId());
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteCommentImpl().insert(entity);
        if (userId != comment.getUserId()) {
            userService.addNoteCommentReplyMessage(userId, comment.getUserId(), comment.getId());
        }
        BiuHoleNoteViewEntity noteInfo = biuDbFactory.getHoleDbFactory().getHoleNoteViewImpl().find(comment.getNoteId());
        Map<String, Object> result = new HashMap<>();
        result.put("comment_num", noteInfo.getCommentNum());
        return new JsonDataResult<>(ResponseConfig.SUCCESS_CODE, "", result);
    }

    public FuncResult getCommentList(long userId, NoteCommentListBean bean) {
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
        result.put("more", 0);
        result.put("list", new ArrayList<Map>());
        long noteId = decodeHash(bean.getNote());
        if (noteId <= 0) {
            result.put("list", new ArrayList<>());
            return new FuncResult(false, "无对应记录", result);
        }
        long last = 0;
        if (!bean.getLast().isEmpty()) {
            last = decodeHash(bean.getLast());
        }
        ProviderOption option = new ProviderOption();
        option.addCondition("note_id", noteId);
        if (bean.getMine() > 0) {
            // 我的评论
            option.addCondition("user_id", userId);
        }
        if (last > 0) {
            if (bean.getOrderby().equals("desc")) {
                option.addCondition("id<" + last);
            } else {
                option.addCondition("id>" + last);
            }
        }
        option.setUsePager(true);
        option.addOrderby("id " + bean.getOrderby());
        option.setOffset(bean.getPage(), bean.getSize());
        option.setLimit(bean.getSize() + 1);
        BiuHoleNoteCommentViewImpl commentImpl = biuDbFactory.getHoleDbFactory().getHoleNoteCommentViewImpl();
        List<BiuHoleNoteCommentViewEntity> rows = commentImpl.list(option);
        if (rows.size() > bean.getSize()) {
            rows.remove(rows.size() - 1);
            result.put("more", 1);
        }
        if (rows.isEmpty()) {
            return new FuncResult(false, "无对应记录", result);
        }
        List<Map> list = processCommentList(rows);
        result.put("list", list);
        return new FuncResult(true, "", result);
    }

    public List<Map> processCommentList(List<BiuHoleNoteCommentViewEntity> rows) {
        List<Map> list = new ArrayList<>();
        BiuUserImpl userImpl = biuDbFactory.getUserDbFactory().getBiuUserImpl();
        rows.forEach(item -> {
            BiuUserEntity commentUser = userImpl.find(item.getCommentUserid());
            BiuUserEntity user = userImpl.find(item.getUserId());
            Map<String, Object> unit = new HashMap<>();
            unit.put("comment_id", encodeHash(item.getId()));
            unit.put("note_id", encodeHash(item.getNoteId()));
            if (item.getCommentId() > 0) {
                unit.put("title", "@" + commentUser.getPenName());
            } else {
                unit.put("title", user.getPenName());
            }
            unit.put("content", item.getContent());
            unit.put("create_time", TimeTool.formatDate(item.getCreatedAt(), "yyyy/MM/dd HH:mm:ss"));
            list.add(unit);
        });
        return list;
    }

    public FuncResult cancelUser(long userId) {
        BiuUserEntity user = biuDbFactory.getUserDbFactory().getBiuUserImpl().find(userId);
        if (user == null) {
            return new FuncResult(false, "用户状态异常");
        }
//        biuDbFactory.getUserDbFactory().getBiuUserImpl().delete(user);
        if (user.getUseStatus() == BiuUserEntity.USER_INVAIL_STATUS) {
            return new FuncResult(false, "用户状态异常");
        }
        user.setUseStatus(BiuUserEntity.USER_INVAIL_STATUS);
        biuDbFactory.getUserDbFactory().getBiuUserImpl().update(user);
        return new FuncResult(true, "");
    }

    /**
     * 用户关联阅读
     * @param userId
     * @param relateId
     * @return
     */
    public FuncResult readUser(long userId, long relateId) {
        if (userId == relateId) {
            return new FuncResult(false, "不能是同一用户哟");
        }
        BiuUserReadLogEntity entity = userService.getUserReadLog(userId, relateId);
        if (entity != null) {
            return new FuncResult(false, "该用户已看过");
        }
        entity = new BiuUserReadLogEntity();
        entity.setUserId(userId);
        entity.setRelateId(relateId);
        biuDbFactory.getUserDbFactory().getBiuUserReadLogImpl().insert(entity);
        return new FuncResult(true, "");
    }
}
