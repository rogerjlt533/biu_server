package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.impl.BiuUserImpl;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.treehole.bean.UserInitUpdateInfoBean;
import com.zuosuo.treehole.bean.UserListBean;
import com.zuosuo.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserProcessor {

    @Autowired
    private UserService userService;
    @Autowired
    private BiuDbFactory biuDbFactory;

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
        BiuUserImpl impl = biuDbFactory.getUserDbFactory().getBiuUserImpl();
        List<String> condition = new ArrayList<>();
        Map<String, Object> user = userService.getUserView(id);
        int custom = bean.getCustom();
        if (custom == 1) {
            // 自定义
            int sex = bean.getSex();
            if (sex > 0) {
                condition.add("sex=" + sex);
            }
            int com_method = bean.getCommunicate();
            if (com_method > 0) {
                condition.add("FIND_IN_SET('" + com_method + "', self_communicate)");
            }
            int[] ages = bean.getAge();
            if (ages != null) {
                condition.add("age>=" + ages[0] + " and age<=" + ages[1]);
            }
        } else if(user != null) {
            // 推荐
            String self_interest = user.get("self_interest") != null ? (String) user.get("self_interest") : "";
            String[] methods = self_interest.split(",");
            if (methods.length > 0) {
                List<String> methodCondition = new ArrayList<>();
                for (String method: methods) {
                    methodCondition.add("FIND_IN_SET(" + method + ", search_communicate)");
                }
                condition.add("(" + String.join(" or ", methodCondition) + ")");
            }
            String self_communicate = user.get("self_communicate") != null ? (String) user.get("self_communicate") : "";
            String[] interests = self_communicate.split(",");
            if (interests.length > 0) {
                List<String> interestCondition = new ArrayList<>();
                for (String interest: interests) {
                    interestCondition.add("FIND_IN_SET(" + interest + ", search_interest)");
                }
                condition.add("(" + String.join(" or ", interestCondition) + ")");
            }
        }
        List<String> sql_options = new ArrayList<>();
        if (condition.size() > 0) {
            sql_options.add("where " + String.join(" and ", condition));
        }
        String sql = "select * from biu_user_views " + String.join(" ", sql_options);
        return null;
    }
}
