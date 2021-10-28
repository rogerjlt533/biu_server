package com.zuosuo.treehole.action;

import com.zuosuo.treehole.bean.LoginInfoBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseAction {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public BaseAction(HttpServletRequest request) {
        this.request = request;
    }

    public BaseAction(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public LoginInfoBean getLoginInfoBean() {
        return (LoginInfoBean) request.getAttribute("user_info");
    }

    public abstract Object run();
}
