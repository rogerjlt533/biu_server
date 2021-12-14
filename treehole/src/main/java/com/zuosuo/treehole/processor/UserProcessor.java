package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.impl.BiuHoleNoteViewImpl;
import com.zuosuo.biudb.impl.BiuUserImpl;
import com.zuosuo.biudb.impl.BiuUserViewImpl;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.response.ResponseConfig;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.CommonTool;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.mybatis.tool.PageTool;
import com.zuosuo.treehole.bean.*;
import com.zuosuo.treehole.result.*;
import com.zuosuo.treehole.service.AreaService;
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

    public UserService getUserService() {
        return userService;
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
        BiuUserViewEntity user = userService.getUserView(id);
        ProviderOption option = new ProviderOption();
        option.setUsePager(true);
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
        if(user != null) {
            option.addCondition("id!=" + user.getId());
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
        } else if(bean.getMethod() == UserListBean.RECOMMEND) {
            // 非登录下系统推荐无数据
            option.addCondition("1=0");
        }
        // 两个月内登录的
        option.addCondition("sort_time>='" + TimeTool.formatDate(TimeTool.getOffsetDate(new Date(), new DiscTime().setMonth(-2))) + "'");
        // 最后记录排重
        if (lastUser != null) {
            option.addCondition("sort_time<'" + TimeTool.formatDate(lastUser.getSortTime()) + '"');
        }
        option.addOrderby("sort_time desc");
        option.setOffset(PageTool.getOffset(bean.getPage(), bean.getSize()));
        option.setLimit(bean.getSize() + 1);
        BiuUserViewImpl impl = biuDbFactory.getUserDbFactory().getBiuUserViewImpl();
        List<BiuUserViewEntity> list = impl.list(option);
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
//        result.put("count", impl.count(option));
        result.put("more", 0);
        if (list.size() > bean.getSize()) {
            list.remove(list.size() - 1);
            result.put("more", 1);
        }
        if (list.isEmpty()) {
            return new FuncResult(false, "无对应记录", result);
        }
        List<UserResult> userList = processList(list, user);
        result.put("list", userList);
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
            result.setName("匿名");
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
        result.setImages(userService.getUserImageList(user.getId(), BiuUserImageEntity.USE_TYPE_INTRODUCE, 6));
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
        if (bean.getMethod().contains("nick")) {
            user.setNick(bean.getNick());
        }
        if (bean.getMethod().contains("image")) {
            BiuUserImageEntity image = userService.setUserImage(userId, BiuUserImageEntity.USE_TYPE_AVATOR, 0, bean.getImage(), 0);
            user.setImage(image.getFile());
        }
        if (bean.getMethod().contains("pen_name")) {
            user.setPenName(bean.getPenName().trim());
        }
        if (bean.getMethod().contains("sex")) {
            user.setSex(bean.getSex());
        }
        if (bean.getMethod().contains("birthday_year")) {
            user.setBirthdayYear(bean.getBirthdayYear());
        }
        if (bean.getMethod().contains("province")) {
            user.setProvince(bean.getProvince());
        }
        if (bean.getMethod().contains("username")) {
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
            user.setTitle(bean.getTitle());
        }
        if (bean.getMethod().contains("introduce")) {
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
        if (bean.getMethod().contains("search_communicate")) {
            userService.setUserCommunicate(userId, BiuUserCommunicateEntity.USE_TYPE_SEARCH, bean.getSearch_communicates());
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
        if (type == BiuUserReportEntity.REPORT_TYPE_REPORT) {
            blackUser(userId, relateId);
        }
        if (images != null && !images.isEmpty()) {
            int imageType = 0;
            if (type == BiuUserReportEntity.REPORT_TYPE_TOUSU) {
                imageType = BiuUserImageEntity.USE_TYPE_TOUSU;
            } else if (type == BiuUserReportEntity.REPORT_TYPE_RECOMMEND) {
                imageType = BiuUserImageEntity.USE_TYPE_RECOMMEND;
            } else if (type == BiuUserReportEntity.REPORT_TYPE_OTHER) {
                imageType = BiuUserImageEntity.USE_TYPE_REPORT_OTHER;
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
            JsonResult result = userService.passFriend(friendId, userId);
            if (result.getCode() == ResponseConfig.SUCCESS_CODE) {
                return new FuncResult(true);
            }
            return new FuncResult(false, result.getMessage());
        } else if(method.equals(ApplyFriendBean.REFUSE)) {
            JsonResult result = userService.refuseFriend(friendId, userId);
            if (result.getCode() == ResponseConfig.SUCCESS_CODE) {
                return new FuncResult(true);
            }
            return new FuncResult(false, result.getMessage());
        } else if(method.equals(ApplyFriendBean.CANCEL)) {
            JsonResult result = userService.removeFriend(friendId, userId);
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
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addOrderby("created_at desc");
        List<BiuUserCollectEntity> rows = biuDbFactory.getUserDbFactory().getBiuUserCollectImpl().list(option);
        if (rows == null) {
            return new FuncResult(false, "无对应记录");
        }
        List<CollectUserResult> list = new ArrayList<>();
        rows.forEach(item -> {
            BiuUserViewEntity entity = userService.getUserView(item.getRelateId());
            int search = entity.getSearchStatus();
            CollectUserResult unit = new CollectUserResult();
            unit.setId(encodeHash(entity.getId()));
            unit.setImage(userService.parseImage(entity.getImage()));
            if (search == BiuUserViewEntity.SEARCH_CLOSE_STATUS) {
                unit.setName("匿名");
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
        if (rows == null) {
            return new FuncResult(false, "无对应记录");
        }
        List<BlackUserResult> list = new ArrayList<>();
        rows.forEach(item -> {
            BiuUserViewEntity entity = userService.getUserView(item.getBlackId());
            int search = entity.getSearchStatus();
            BlackUserResult unit = new BlackUserResult();
            unit.setId(encodeHash(entity.getId()));
            unit.setImage(userService.parseImage(entity.getImage()));
            if (search == BiuUserViewEntity.SEARCH_CLOSE_STATUS) {
                unit.setName("匿名");
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
        });
        return new FuncResult(true, "", list);
    }

    /**
     * 获取笔友列表
     * @param userId
     * @return
     */
    public FuncResult getUserFriendList(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("confirm_status", BiuUserFriendMemberEntity.PASS_STATUS);
        List<BiuUserFriendMemberEntity> members = biuDbFactory.getUserDbFactory().getBiuUserFriendMemberImpl().list(option);
        if (members == null) {
            return new FuncResult(false, "无对应记录");
        }
        List<String> friendIdList = members.stream().map(item -> String.valueOf(item.getFriendId())).collect(Collectors.toList());
        option = new ProviderOption();
        option.addCondition("id in (" + String.join(",", friendIdList) + ")");
        option.addCondition("confirm_status", BiuUserFriendEntity.PASS_STATUS);
        List<BiuUserFriendEntity> friends = biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().list(option);
        if (friends == null) {
            return new FuncResult(false, "无对应记录");
        }
        List<UserFriendResult> list = userService.processFriendList(friends, userId);
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
        if (bean.getMethod().equals(SignCommunicateBean.SEND)) {
            BiuUserFriendEntity friend = biuDbFactory.getUserDbFactory().getBiuUserFriendImpl().find(decodeHash(bean.getFriend()));
            if (friend == null) {
                return new FuncResult(false, "无对应记录");
            }
            if (friend.getConfirmStatus() != BiuUserFriendEntity.PASS_STATUS) {
                return new FuncResult(false, "好友记录无效");
            }
            result = userService.sendFriendCommunicate(friend, userId);
        } else {
            BiuUserFriendCommunicateLogEntity log = biuDbFactory.getUserDbFactory().getBiuUserFriendCommunicateLogImpl().find(decodeHash(bean.getLog()));
            if (log == null) {
                return new FuncResult(false, "无对应记录");
            }
            if (log.getReceiveStatus() == BiuUserFriendCommunicateLogEntity.RECEIVED) {
                return new FuncResult(false, "邮件已接收");
            }
            if (log.getReceiveUser() != userId) {
                return new FuncResult(false, "您不是收件人，请等待笔友确认哟");
            }
            result = userService.receiveFriendCommunicate(log);
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
        long notice_count = biuDbFactory.getUserDbFactory().getBiuMessageImpl().count(option);
        result.put("notice", notice_count);
        option = new ProviderOption();
        option.addCondition("dest_id", userId);
        types = Arrays.asList(BiuMessageEntity.MESSAGE_COMMMENT, BiuMessageEntity.MESSAGE_FAVOR, BiuMessageEntity.MESSAGE_REPLY).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(","));
        option.addCondition("message_type in (" + types + ")");
        option.addCondition("read_status", BiuMessageEntity.READ_WAITING);
        long message_count = biuDbFactory.getUserDbFactory().getBiuMessageImpl().count(option);
        result.put("message", message_count);
        option = new ProviderOption();
        option.addCondition("dest_id", userId);
        types = Arrays.asList(BiuMessageEntity.PUBLIC_NOTICE, BiuMessageEntity.PUBLIC_ACTIVE, BiuMessageEntity.PUBLIC_UPDATE).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(","));
        option.addCondition("message_type in (" + types + ")");
        option.addCondition("read_status", BiuMessageEntity.READ_WAITING);
        long public_count = biuDbFactory.getUserDbFactory().getBiuMessageImpl().count(option);
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
        List<BiuMessageEntity> rows = biuDbFactory.getUserDbFactory().getBiuMessageImpl().list(option);
        if (rows == null) {
            return new FuncResult(false, "无对应记录");
        }
        BiuUserViewEntity user = userService.getUserView(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
        result.put("more", 0);
        if (rows.size() > bean.getSize()) {
            rows.remove(rows.size() - 1);
            result.put("more", 1);
        }
        if (rows.isEmpty()) {
            return new FuncResult(false, "无对应记录", result);
        }
        List<UserMessageResult> messageList = processMessageList(rows, user);
        result.put("list", messageList);
        return new FuncResult(true, "", result);
    }

    public List<UserMessageResult> processMessageList(List<BiuMessageEntity> rows, BiuUserViewEntity user) {
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
            if (item.getMessageType() == BiuMessageEntity.NOTICE_APPLY || item.getMessageType() == BiuMessageEntity.NOTICE_FRIEND) {
                long sourceId = item.getSourceId();
                long destId = item.getDestId();
                if (destId == sourceId) {
                    destId = item.getRelateId();
                }
                unit.setUseFriendApply(1);
                BiuUserFriendEntity friendEntity = userService.getUserFriend(sourceId, destId);
                if (friendEntity != null) {
                    long friendId = userService.getFriendId(friendEntity, user.getId());
                    BiuUserViewEntity friendUser = userService.getUserView(friendId);
                    unit.getFriendApply().setId(encodeHash(friendEntity.getId()));
                    unit.getFriendApply().setUser(encodeHash(user.getId()));
                    unit.getFriendApply().setFriend(encodeHash(friendId));
                    unit.getFriendApply().setName(user.getPenName());
                    unit.getFriendApply().setImage(userService.parseImage(friendUser.getImage()));
                    unit.getFriendApply().setDesc(userService.getUserDesc(user));
                    if (friendId != user.getId() && friendEntity.getConfirmStatus() == BiuUserFriendEntity.WAITING_STATUS) {
                        unit.getFriendApply().setAllowAuth(UserMessageFriendResult.ALLOW_AUTH);
                    }
                    if (friendEntity.getConfirmStatus() != BiuUserFriendEntity.REFUSE_STATUS) {
                        unit.getFriendApply().setStatus(UserMessageFriendResult.ENABLE);
                    }
                }
            }
            list.add(unit);
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
        note.setIsPrivate(bean.getIsSelf());
        note.setNickShow(bean.getNick());
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().insert(note);
        if (note.getId() > 0) {
            userService.processNoteOther(userId, note.getId(), bean.getLabel(), bean.getMood(), bean.getImages());
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
        note.setIsPrivate(bean.getIsSelf());
        note.setNickShow(bean.getNick());
        biuDbFactory.getHoleDbFactory().getBiuHoleNoteImpl().update(note);
        userService.processNoteOther(userId, note.getId(), bean.getLabel(), bean.getMood(), bean.getImages());
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
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
        result.put("more", 0);
        ProviderOption option = new ProviderOption();
        if (bean.getMethod().equals(NoteListBean.INDEX)) {
            option.addCondition("is_private", BiuHoleNoteEntity.PRIVATE_NO);
        } else if(bean.getMethod().equals(NoteListBean.MINE)) {

        } else if(bean.getMethod().equals(NoteListBean.MINE)) {

        } else {
            result.put("list", new ArrayList<>());
            return new FuncResult(false, "", result);
        }
        if (!bean.getLast().isEmpty()) {
            long last = decodeHash(bean.getLast());
            option.addCondition("id<" + last);
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
            if (noteUser != null) {
                long moods = 0, label = 0;
                BiuMoodEntity moodEntity = null;
                BiuLabelEntity labelEntity = null;
                if (!item.getMoods().isEmpty()) {
                    moods = Arrays.stream(item.getMoods().replaceAll("'", "").split(",")).map(value -> Long.parseLong(value)).reduce(Long::sum).orElse(0L);
                    moodEntity = biuDbFactory.getHoleDbFactory().getMoodImpl().find(moods);
                }
                if (!item.getLabels().isEmpty()) {
                    label = Arrays.stream(item.getLabels().replaceAll("'", "").split(",")).map(value -> Long.parseLong(value)).reduce(Long::sum).orElse(0L);
                    labelEntity = biuDbFactory.getHoleDbFactory().getLabelImpl().find(label);
                }
                Map<String, Object> unit = new HashMap<>();
                unit.put("id", encodeHash(item.getId()));
                unit.put("user", encodeHash(noteUser.getId()));
                if (user.getId() != noteUser.getId() && item.getNickShow() == BiuHoleNoteEntity.NICK_YES) {
                    unit.put("name", "匿名");
                    unit.put("image", "");
                } else {
                    unit.put("name", noteUser.getPenName());
                    unit.put("image", userService.parseImage(noteUser.getImage()));
                }
                unit.put("mood", moodEntity != null ? moods : 0);
                unit.put("mood_emoj", moodEntity != null ? moodEntity.getEmoj() : "");
                unit.put("mood_tag", moodEntity != null ? moodEntity.getTag() : "");
                unit.put("label", labelEntity != null ? label : 0);
                unit.put("label_tag", labelEntity != null ? labelEntity.getTag() : "");
                unit.put("content", item.getContent());
                unit.put("favor_num", item.getFavorNum());
                unit.put("comment_num", item.getCommentNum());
                unit.put("create_time", TimeTool.friendlyTime(item.getCreatedAt()));
                unit.put("is_collect", 0);
                unit.put("allow_report", 0);
                if (user.getId() != noteUser.getId()) {
                    unit.put("is_collect", userCollectService.isCollected(user.getId(), noteUser.getId()) ? 1 : 0);
                    unit.put("allow_report", 1);
                }
                Map favorResult = userService.getNoteFavorCondition(item.getId());
                unit.put("favor_images", favorResult.get("images"));
                unit.put("images", userService.getNoteImages(item.getId(), BiuUserImageEntity.USE_TYPE_NOTE, 3));
                list.add(unit);
            }
        });
        return list;
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
        } else if(!bean.getId().isEmpty()) {
            result = userService.removeLabel(userId, decodeHash(bean.getId()));
        } else {
            result = new JsonDataResult<>("操作错误");
        }
        if (result.getCode() == ResponseConfig.SUCCESS_CODE) {
            return new FuncResult(true, "", result.getData());
        }
        return new FuncResult(false, result.getMessage());
    }
}
