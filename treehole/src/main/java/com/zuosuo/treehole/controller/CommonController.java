package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.common.UploadTokenAction;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.tool.QiniuTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class CommonController {

    @Autowired
    private QiniuTool qiniuTool;

    /**
     * 七牛上传token
     * @param request
     * @return
     */
    @PostMapping("/api/upload/token")
    @Login
    public JsonDataResult<Map> myInfo(HttpServletRequest request) {
        return new UploadTokenAction(request, qiniuTool).run();
    }
}
