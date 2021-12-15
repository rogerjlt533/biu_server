package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.note.*;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.bean.*;
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
     * 树洞选择项信息
     * @param request
     * @return
     */
    @PostMapping("/api/hole/note/selections")
    @Login
    public JsonDataResult<Map> selections(HttpServletRequest request) {
        return new NoteSelectionAction(request, userProcessor).run();
    }

    /**
     * 发送树洞消息
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/create")
    @Login
    public JsonResult create(HttpServletRequest request, @RequestBody CreateNoteBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new CreateNoteAction(request, bean, userProcessor).run();
    }

    /**
     * 编辑树洞消息
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/edit")
    @Login
    public JsonResult edit(HttpServletRequest request, @RequestBody CreateNoteBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new EditNoteAction(request, bean, userProcessor).run();
    }

    /**
     * 树洞消息详情
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/info")
    @Login
    public JsonDataResult<Map> info(HttpServletRequest request, @RequestBody NoteInfoBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new NoteInfoAction(request, bean, userProcessor).run();
    }

    /**
     * 删除树洞消息
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/remove")
    @Login
    public JsonResult remove(HttpServletRequest request, @RequestBody RemoveNoteBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new RemoveNoteAction(request, bean, userProcessor).run();
    }

    /**
     * 点赞树洞消息
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/favor")
    @Login
    public JsonDataResult<Map> favor(HttpServletRequest request, @RequestBody NoteInfoBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new NoteFavorAction(request, bean, userProcessor).run();
    }

    /**
     * 评论树洞消息
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/comment")
    @Login
    public JsonDataResult<Map> comment(HttpServletRequest request, @RequestBody NoteCommentBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new NoteCommentAction(request, bean, userProcessor).run();
    }

    /**
     * 树洞消息列表
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/list")
    @Login(open = true)
    public JsonDataResult<Map> list(HttpServletRequest request, @RequestBody NoteListBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new NoteListAction(request, bean, userProcessor).run();
    }

    /**
     * 树洞消息评论列表
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/hole/note/comment/list")
    @Login(open = true)
    public JsonDataResult<Map> comment(HttpServletRequest request, @RequestBody NoteCommentListBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new NoteCommentListAction(request, bean, userProcessor).run();
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
