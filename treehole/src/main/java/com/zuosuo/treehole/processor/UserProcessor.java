package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.entity.BiuUserViewEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.mybatis.tool.PageTool;
import com.zuosuo.treehole.bean.UserInitUpdateInfoBean;
import com.zuosuo.treehole.bean.UserListBean;
import com.zuosuo.treehole.result.UserResult;
import com.zuosuo.treehole.service.AreaService;
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
    private BiuDbFactory biuDbFactory;
    @Autowired
    private HashTool hashTool;
    @Autowired
    private AreaService areaService;

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
        biuDbFactory.getUserDbFactory().getBiuUserImpl().update(user);
        return new FuncResult(true);
    }

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
                    communicateCondition.add("FIND_IN_SET(" + communicate + ", self_communicate)");
                }
                option.addCondition("(" + String.join(" or ", communicateCondition) + ")");
            }
        }
        // 两个月内登录的
        option.addCondition("sort_time>=" + TimeTool.formatDate(TimeTool.getOffsetDate(new Date(), new DiscTime().setMonth(-2))));
        option.addOrderby("sort_time desc");
        option.setOffset(PageTool.getOffset(bean.getPage(), PageTool.DEFAULT_SIZE));
        option.setLimit(PageTool.DEFAULT_SIZE);
        List<BiuUserViewEntity> list = biuDbFactory.getUserDbFactory().getBiuUserViewImpl().list(option);
        Map<String, Object> result = new HashMap<>();
        result.put("page", PageTool.parsePage(bean.getPage()));
        result.put("size", bean.getSize());
        if (list.isEmpty()) {
            return new FuncResult(false, "无对应记录", result);
        }
        List<UserResult> userList = processList(list, user);
        result.put("list", userList);
        return new FuncResult(true, "", result);
    }

    private List<UserResult> processList(List<BiuUserViewEntity> list, BiuUserViewEntity user) {
        List<UserResult> result = new ArrayList<>();
        list.forEach(item -> {
            UserResult unit = new UserResult();
            unit.setId(hashTool.getHashids(4).encode(item.getId()));
            unit.setName(item.getNick());
            unit.setAge(item.getAge());
            unit.setIntroduce(item.getIntroduce());
            unit.setProvince(areaService.getArea(item.getProvince()));
            unit.setSex(item.getSexTag());
            unit.setSortTime(TimeTool.friendlyTime(item.getSortTime()));
            unit.setImages(userService.getUserImageList(item.getId()));
            unit.setInterests(userService.getUserInterestList(item.getId()));
            if (item.getSelfCommunicate() != null) {
                unit.setCommunicates(Arrays.asList(item.getSelfCommunicate().replace("'", "").split(",")).stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList()));
            } else {
                unit.setCommunicates(new ArrayList<>());
            }
            result.add(unit);
        });
        return result;
    }
}
