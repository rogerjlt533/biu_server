package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.treehole.action.common.UploadAction;
import com.zuosuo.treehole.action.common.UploadTokenAction;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.tool.QiniuTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class CommonController {

    @Autowired
    private QiniuTool qiniuTool;
    @Autowired
    private UserProcessor userProcessor;

    /**
     * 七牛上传token
     * @param request
     * @return
     */
    @PostMapping("/api/upload/token")
    @Login
    public JsonDataResult<Map> uploadToken(HttpServletRequest request) {
        return new UploadTokenAction(request, qiniuTool).run();
    }

    @PostMapping("/api/upload")
    @Login
    public JsonDataResult<Map> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new JsonDataResult<>("文件不能为空");
        }
        return new UploadAction(request, userProcessor, qiniuTool, file).run();
    }
}
