package com.zuosuo.treehole.Handle;

import com.zuosuo.auth.jwt.JWTResult;
import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.bean.LoginInfoBean;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.tool.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;

public class LoginInterceptor implements HandlerInterceptor {

    private BiuDbFactory biuDbFactory;
    private UserService userService;

    public LoginInterceptor(BiuDbFactory biuDbFactory, UserService userService) {
        this.biuDbFactory = biuDbFactory;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(Login.class)) {
            return true;
        }
        Login loginAnnotation = method.getAnnotation(Login.class);
        String token = request.getHeader("token");
        LoginInfoBean loginInfo = getLoginInfo(token);
        System.out.println("user_id:" + loginInfo.getUserId());
        if (!loginAnnotation.open() && loginInfo.getUserId() == 0) {
            return error(response, "未登录");
        }
        if (loginInfo.getUserId() > 0) {
            BiuUserEntity user = biuDbFactory.getUserDbFactory().getBiuUserImpl().find(loginInfo.getUserId());
            if (user == null) {
                return error(response, "用户信息不存在");
            } else if(user.getUseStatus() == BiuUserEntity.USER_INVAIL_STATUS) {
                return error(response, "用户状态异常");
            }
            userService.setUserSortTime(user.getId());
            userService.syncUserIndex(user.getId());
            if (loginAnnotation.penuser() && user.getIsPenuser() == BiuUserEntity.USER_NOT_PEN) {
                return error(response, 502, "您未注册为笔友，请先完成注册");
            }
        }
        request.setAttribute("user_info", loginInfo);
        return true;
    }

    private LoginInfoBean getLoginInfo(String token) {
        if (token == null) {
            return new LoginInfoBean();
        }
        if (token.isEmpty()) {
            return new LoginInfoBean();
        }
        JWTResult jwt = new JwtTool().getResult(token);
        if (jwt.getExpireAt().compareTo(new Date()) <= 0) {
            return new LoginInfoBean();
        }
        return new LoginInfoBean(Long.parseLong(jwt.getData("user_id")));
    }

    private boolean error(HttpServletResponse response, String message) throws IOException {
        return error(response, 501, message);
    }

    private boolean error(HttpServletResponse response, int code, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.println(JsonTool.toJson(new JsonResult(code, message)));
        return false;
    }
}
