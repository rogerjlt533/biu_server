package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.user.MyInfoAction;
import com.zuosuo.treehole.action.user.UserInitUpdateInfoAction;
import com.zuosuo.treehole.action.user.UserInterestListAction;
import com.zuosuo.treehole.action.user.UserListAction;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.bean.UserInitUpdateInfoBean;
import com.zuosuo.treehole.bean.UserListBean;
import com.zuosuo.treehole.bean.VerifyResult;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.result.MyInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserProcessor userProcessor;

    /**
     * 用户登录信息初始化
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/init")
    @Login
    public JsonResult initInfo(HttpServletRequest request, @RequestBody UserInitUpdateInfoBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new UserInitUpdateInfoAction(request, bean, userProcessor).run();
    }

    /**
     * 用户列表
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/list")
    @Login(open = true)
    public JsonDataResult<Map> list(HttpServletRequest request, @RequestBody UserListBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new UserListAction(request, bean, userProcessor).run();
    }

    /**
     * 用户爱好列表
     * @param request
     * @return
     */
    @PostMapping("/api/user/interests")
    @Login
    public JsonDataResult<Map> interests(HttpServletRequest request) {
        return new UserInterestListAction(request, userProcessor).run();
    }

    /**
     * 我的信息
     * @param request
     * @return
     */
    @PostMapping("/api/my/info")
    @Login
    public JsonDataResult<MyInfoResult> myInfo(HttpServletRequest request) {
        return new MyInfoAction(request, userProcessor).run();
    }
}
