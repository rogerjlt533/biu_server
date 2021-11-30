package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.impl.BiuUserViewImpl;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.CommonTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.mybatis.tool.PageTool;
import com.zuosuo.treehole.bean.UserInfoBean;
import com.zuosuo.treehole.bean.UserInitUpdateInfoBean;
import com.zuosuo.treehole.bean.UserListBean;
import com.zuosuo.treehole.result.MyInfoResult;
import com.zuosuo.treehole.result.UserInterestResult;
import com.zuosuo.treehole.result.UserResult;
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
        BiuUserImageEntity image = userService.setUserImage(id, BiuUserImageEntity.USE_TYPE_AVATOR, bean.getImage(), 0);
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
            option.addCondition("FIND_IN_SET('" + com_method + "', self_communicate)");
        }
        int[] ages = bean.getAge();
        if (ages != null) {
            option.addCondition("age>=" + ages[0] + " and age<=" + ages[1]);
        }
        if(user != null) {
            option.addCondition("id!=" + user.getId());
            int self_age = user.getAge();
            if (self_age > 0) {
                option.addCondition("match_start_age<=" + self_age + " and match_end_age>=" + self_age);
            }
            int self_sex = user.getSex();
            if (self_sex > 0) {
                option.addCondition("FIND_IN_SET('" + self_sex + "', search_sex)");
            }
            String self_communicate = user.getSelfCommunicate() != null ? user.getSelfCommunicate() : "";
            String[] communicates = self_communicate.split(",");
            if (communicates.length > 0) {
                List<String> communicateCondition = new ArrayList<>();
                for (String communicate: communicates) {
                    if (!communicate.isEmpty()) {
                        communicateCondition.add("FIND_IN_SET(" + communicate + ", search_communicate)");
                    }
                }
                if (!communicateCondition.isEmpty()) {
                    option.addCondition("(" + String.join(" or ", communicateCondition) + ")");
                }
            }
            option.addCondition("!FIND_IN_SET('" + user.getId() + "', protected_user)");
        } else {
            option.addCondition("(age=0 or ISNULL(search_sex))");
        }
        // 两个月内登录的
        option.addCondition("sort_time>='" + TimeTool.formatDate(TimeTool.getOffsetDate(new Date(), new DiscTime().setMonth(-2))) + "'");
        option.addOrderby("sort_time desc");
        option.setOffset(PageTool.getOffset(bean.getPage(), PageTool.DEFAULT_SIZE));
        option.setLimit(PageTool.DEFAULT_SIZE);
        BiuUserViewImpl impl = biuDbFactory.getUserDbFactory().getBiuUserViewImpl();
        List<BiuUserViewEntity> list = impl.list(option);
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
        result.put("count", impl.count(option));
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
            unit.setId(hashTool.getHashids(4).encode(item.getId()));
            unit.setName(item.getPenName());
            unit.setAge(item.getAge());
            unit.setTitle(item.getTitle());
            unit.setIntroduce(item.getIntroduce());
            unit.setProvince(areaService.getArea(item.getProvince()));
            unit.setSex(item.getSexTag());
            unit.setSortTime(TimeTool.friendlyTime(item.getSortTime()));
            unit.setImages(userService.getUserImageList(item.getId(), BiuUserImageEntity.USE_TYPE_INTRODUCE));
            unit.setInterests(userService.getUserInterestSimpleList(item.getId()));
            if (item.getSelfCommunicate() != null && !item.getSelfCommunicate().isEmpty()) {
                unit.setCommunicates(Arrays.asList(item.getSelfCommunicate().replace("'", "").split(",")).stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList()));
            } else {
                unit.setCommunicates(new ArrayList<>());
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
            BiuUserImageEntity image = userService.setUserImage(userId, BiuUserImageEntity.USE_TYPE_AVATOR, bean.getImage(), 0);
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
        if (bean.getMethod().contains("image")) {
            userService.setUserImage(userId, BiuUserImageEntity.USE_TYPE_INTRODUCE, bean.getImages());
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
                userService.setUserImage(userId, imageType, images);
            }
        }
        return new FuncResult(true);
    }
}
