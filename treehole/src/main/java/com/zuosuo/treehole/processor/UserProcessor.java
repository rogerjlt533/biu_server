package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.treehole.bean.UserInitUpdateInfoBean;
import com.zuosuo.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
