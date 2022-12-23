package com.zuosuo.treehole.controller;

import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.response.JsonResult;
import com.zuosuo.treehole.action.user.*;
import com.zuosuo.treehole.annotation.Login;
import com.zuosuo.treehole.bean.*;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.result.*;
import com.zuosuo.treehole.tool.WechatTool;
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
    @Autowired
    private WechatTool wechatTool;
    @Autowired
    private BiuRedisFactory biuRedisFactory;

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
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/api/user/cancel")
    @Login
    public JsonResult cancelUser(HttpServletRequest request) {
        return new CancelUserAction(request, userProcessor).run();
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
     * 用户首页
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/home")
    @Login(open = true)
    public JsonDataResult<UserHomeResult> userHome(HttpServletRequest request, @RequestBody UserHomeBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonDataResult<>(verify.getMessage());
        }
        return new UserHomeAction(request, bean, userProcessor).run();
    }

    /**
     * 用户爱好分组列表
     * @param request
     * @return
     */
    @PostMapping("/api/user/interest/list")
    @Login(open = true)
    public JsonDataResult<List> userInterestList(HttpServletRequest request) {
        return new UserGroupInterestListAction(request, userProcessor).run();
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
     * 用户拉黑
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/black")
    @Login
    public JsonResult black(HttpServletRequest request, @RequestBody UserBlackBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new UserBlackAction(request, bean, userProcessor).run();
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
    @Login(penuser = true)
    public JsonResult applyFriend(HttpServletRequest request, @RequestBody ApplyFriendBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new ApplyFriendAction(request, bean, userProcessor).run();
    }

    /**
     * 笔友寄信收信
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/friend/letter")
    @Login
    public JsonResult friendLetter(HttpServletRequest request, @RequestBody SignCommunicateBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new SignCommunicateAction(request, bean, userProcessor).run();
    }

    /**
     * 用户笔友列表
     * @param request
     * @return
     */
    @PostMapping("/api/user/friend/list")
    @Login
    public JsonDataResult<List<UserFriendResult>> friendUserList(HttpServletRequest request) {
        return new UserFriendListAction(request, userProcessor).run();
    }

    /**
     * 用户笔友通讯历史
     * @param request
     * @return
     */
    @PostMapping("/api/user/friend/communicate/logs")
    @Login
    public JsonDataResult<List<Map>> friendUserList(HttpServletRequest request, @RequestBody UserFriendCommunicateLogBean bean) {
        return new UserFriendCommunicateLogsAction(request, bean, userProcessor).run();
    }

    /**
     * 用户消息列表
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/message/list")
    @Login
    public JsonResult messageList(HttpServletRequest request, @RequestBody UserMessageListBean bean) {
        VerifyResult verify = bean.verify();
        if (!verify.isStatus()) {
            return new JsonResult(verify.getMessage());
        }
        return new UserMessageListAction(request, bean, userProcessor).run();
    }

    /**
     * 待读用户消息数
     * @param request
     * @return
     */
    @PostMapping("/api/user/message/tip")
    @Login
    public JsonResult readingMessageList(HttpServletRequest request) {
        return new UserMessageTipAction(request, userProcessor).run();
    }

    /**
     * 用户消息标记已读
     * @param request
     * @return
     */
    @PostMapping("/api/user/message/read")
    @Login
    public JsonResult readMessage(HttpServletRequest request, @RequestBody UserMessageReadBean bean) {
        return new UserMessageReadAction(request, bean, userProcessor).run();
    }

    /**
     * 全部消息标记已读
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/message/all/read")
    @Login
    public JsonResult readAllMessage(HttpServletRequest request, @RequestBody UserMessageAllReadBean bean) {
        return new UserMessageAllReadAction(request, bean, userProcessor).run();
    }

    /**
     * 删除用户消息
     * @param request
     * @return
     */
    @PostMapping("/api/user/message/remove")
    @Login
    public JsonResult removeMessage(HttpServletRequest request, @RequestBody UserMessageRemoveBean bean) {
        return new UserMessageRemoveAction(request, bean, userProcessor).run();
    }

    /**
     * 用户已阅读
     * @param request
     * @return
     */
    @PostMapping("/api/user/read")
    @Login
    public JsonResult readUser(HttpServletRequest request, @RequestBody UserReadBean bean) {
        return new UserReadAction(request, bean, userProcessor).run();
    }

    /**
     * 发送用户好友私信
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/friend/message/send")
    @Login
    public JsonResult sendUserFriendMessage(HttpServletRequest request, @RequestBody UserFriendMessageBean bean) {
        return new UserFriendMessageAction(request, bean, userProcessor, biuRedisFactory).run();
    }

    /**
     * 撤销用户好友私信
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/friend/message/cancel")
    @Login
    public JsonResult cancelUserFriendMessage(HttpServletRequest request, @RequestBody CancelUserFriendMessageBean bean) {
        return new CancelUserFriendMessageAction(request, bean, userProcessor).run();
    }

    /**
     * 用户好友私信列表
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/api/user/friend/message/list")
    @Login
    public JsonResult getUserFriendMessageList(HttpServletRequest request, @RequestBody UserFriendMessageListBean bean) {
        return new UserFriendMessageListAction(request, bean, userProcessor).run();
    }

    /**
     * 私信笔友用户列表
     * @param request
     * @return
     */
    @PostMapping("/api/user/friend/message/users")
    @Login
    public JsonResult getPrivateMessageUserList(HttpServletRequest request, @RequestBody PrivateMessageUserListBean bean) {
        return new PrivateMessageUserListAction(request, bean, userProcessor).run();
    }

    /**
     * 删除笔友私信
     * @param request
     * @return
     */
    @PostMapping("/api/user/friend/message/remove")
    @Login
    public JsonResult removeUserPrivateMessage(HttpServletRequest request, @RequestBody RemoveUserFriendMessageBean bean) {
        return new RemoveUserFriendMessageAction(request, bean, userProcessor).run();
    }

    /**
     * 用户通知模板列表
     * @param request
     * @return
     */
    @PostMapping("/api/user/notice/auth/list")
//    @Login
    public JsonResult userNoticeAuthList(HttpServletRequest request) {
        return new UserNoticeAuthListAction(request, userProcessor).run();
    }

    /**
     * 获取access_token
     * @param request
     * @return
     */
    @PostMapping("/api/access_token")
    @Login
    public JsonResult getAccessToken(HttpServletRequest request) {
        return new GetAccessTokenAction(request, wechatTool).run();
    }
}
