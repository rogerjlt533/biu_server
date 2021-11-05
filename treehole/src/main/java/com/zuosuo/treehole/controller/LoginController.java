package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.login.LoginCodeAction;
import com.zuosuo.treehole.bean.LoginCodeBean;
import com.zuosuo.treehole.bean.VerifyResult;
import com.zuosuo.treehole.processor.WechatProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private WechatProcessor wechatProcessor;

    /**
     * 用户小程序code登录
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/login/wechat/code")
    public JsonDataResult<Map> code2Session(HttpServletRequest request, @RequestBody LoginCodeBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<Map>(verify.getMessage());
        }
        return new LoginCodeAction(request, bean, wechatProcessor).run();
    }
}
