package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.user.UserInitUpdateInfoAction;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.bean.UserInitUpdateInfoBean;
import com.zuosuo.treehole.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserProcessor userProcessor;

    @PostMapping("/api/user/init")
    @Login
    public JsonResult initInfo(HttpServletRequest request, @RequestBody UserInitUpdateInfoBean bean) {
        return new UserInitUpdateInfoAction(request, bean, userProcessor).run();
    }
}
