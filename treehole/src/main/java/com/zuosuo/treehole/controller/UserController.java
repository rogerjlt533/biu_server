package com.zuosuo.treehole.controller;

import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.user.*;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.bean.*;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.result.BlackUserResult;
import com.zuosuo.treehole.result.CollectUserResult;
import com.zuosuo.treehole.result.MyInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
     * 我的信息
     * @param request
     * @return
     */
    @PostMapping("/api/my/info")
    @Login
    public JsonDataResult<MyInfoResult> myInfo(HttpServletRequest request) {
        return new MyInfoAction(request, userProcessor).run();
    }

    /**
     * 用户状态修改
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/my/update/status")
    @Login
    public JsonDataResult<Map> updateStatus(HttpServletRequest request, @RequestBody UserStatusBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new UserStatusAction(request, bean, userProcessor).run();
    }

    /**
     * 用户信息修改
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/my/update/info")
    @Login
    public JsonResult updateInfo(HttpServletRequest request, @RequestBody UserInfoBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new UpdateInfoAction(request, bean, userProcessor).run();
    }

    /**
     * 用户建议举报
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/report")
    @Login
    public JsonResult report(HttpServletRequest request, @RequestBody UserReportBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new UserReportAction(request, bean, userProcessor).run();
    }

    /**
     * 用户关注与取消关注
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/collect")
    @Login
    public JsonResult collect(HttpServletRequest request, @RequestBody UserCollectBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new UserCollectAction(request, bean, userProcessor).run();
    }

    /**
     * 用户取消拉黑
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/black/cancel")
    @Login
    public JsonResult cancelBlack(HttpServletRequest request, @RequestBody CancelBlackBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new CancelBlackAction(request, bean, userProcessor).run();
    }

    /**
     * 用户关注列表
     * @param request
     * @return
     */
    @PostMapping("/api/user/collect/list")
    @Login
    public JsonDataResult<List<CollectUserResult>> collectUserList(HttpServletRequest request) {
        return new CollectedUserListAction(request, userProcessor).run();
    }

    /**
     * 用户拉黑列表
     * @param request
     * @return
     */
    @PostMapping("/api/user/black/list")
    @Login
    public JsonDataResult<List<BlackUserResult>> blackUserList(HttpServletRequest request) {
        return new BlackedUserListAction(request, userProcessor).run();
    }

    /**
     * 用户好友申请处理
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/friend/apply")
    @Login
    public JsonResult applyFriend(HttpServletRequest request, @RequestBody ApplyFriendBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new ApplyFriendAction(request, bean, userProcessor).run();
    }
}
