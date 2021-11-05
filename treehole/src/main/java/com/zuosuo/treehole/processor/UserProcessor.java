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
        if(user != null) {
            condition.add("id!=" + user.get("id"));
            int self_age = (int) user.get("age");
            if (self_age > 0) {
                condition.add("match_start_age<=" + self_age + " and match_end_age>=" + self_age);
            }
            int self_sex = (int) user.get("sex");
            if (self_sex > 0) {
                condition.add("FIND_IN_SET('" + self_sex + "', search_sex)");
            }
            String self_communicate = user.get("self_communicate") != null ? (String) user.get("self_communicate") : "";
            String[] communicates = self_communicate.split(",");
            if (communicates.length > 0) {
                List<String> communicateCondition = new ArrayList<>();
                for (String communicate: communicates) {
                    communicateCondition.add("FIND_IN_SET(" + communicate + ", self_communicate)");
                }
                condition.add("(" + String.join(" or ", communicateCondition) + ")");
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
