package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.ResponseConfig;
import com.zuosuo.treehole.action.login.LoginCodeAction;
import com.zuosuo.treehole.bean.LoginCodeBean;
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

    @PostMapping("/api/login/wechat/code")
    public JsonDataResult<Map> code2Session(HttpServletRequest request, @RequestBody LoginCodeBean bean) {
        if (bean.getCode() != null && bean.getCode().isEmpty()) {
            return new JsonDataResult<>(ResponseConfig.ERROR_CODE, "用户code不能为空");
        }
        return new LoginCodeAction(request, bean, wechatProcessor).run();
    }
}
