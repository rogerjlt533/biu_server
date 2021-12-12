package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.note.CreateNoteAction;
import com.zuosuo.treehole.action.note.OperateLabelAction;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.bean.CreateNoteBean;
import com.zuosuo.treehole.bean.OperateLabelBean;
import com.zuosuo.treehole.bean.VerifyResult;
import com.zuosuo.treehole.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class NoteController {

    @Autowired
    private UserProcessor userProcessor;

    /**
     * 发送树洞消息
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/create")
    @Login
    public JsonDataResult<Map> create(HttpServletRequest request, @RequestBody CreateNoteBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new CreateNoteAction(request, bean, userProcessor).run();
    }

    /**
     * 操作树洞标签
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/label/operate")
    @Login
    public JsonResult operateLabel(HttpServletRequest request, @RequestBody OperateLabelBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new OperateLabelAction(request, bean, userProcessor).run();
    }
}
