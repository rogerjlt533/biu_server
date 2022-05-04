package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.BiuMessageEntity;
import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.biudb.redis.BiuRedisFactory;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.HttpTool;
import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.config.SystemOption;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.tool.HashTool;
import com.zuosuo.treehole.tool.JwtTool;
import com.zuosuo.wechat.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WechatProcessor {

    @Autowired
    private MiniWechatConfig miniWechatConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private BiuDbFactory biuDbFactory;
    @Autowired
    private HashTool hashTool;
    @Autowired
    private BiuRedisFactory biuRedisFactory;

    public MiniWechatConfig getMiniWechatConfig() {
        return miniWechatConfig;
    }

    public FuncResult loginCode(HttpServletRequest request, SessionInfo session) {
        if (session.getOpenid().isEmpty()) {
            return new FuncResult();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("is_penuser", 0);
        String openid = session.getOpenid();
        String unionid = session.getUnionid();
        String openidKey = SystemOption.USER_OPENID_KEY.getValue().replace("#OPENID#", openid);
        FuncResult opencache = biuRedisFactory.getBiuRedisTool().getValueOperator().get(openidKey, Long.class);
        BiuUserEntity user = null;
        if (opencache.isStatus() && (long) opencache.getResult() > 0) {
            user = userService.find((long) opencache.getResult());
        } else {
            user = userService.getUserByOpenid(openid);
            if (user != null) {
                biuRedisFactory.getBiuRedisTool().getValueOperator().set(openidKey, user.getId());
            }
        }
        if (user == null) {
            user = new BiuUserEntity();
            user.setUsername("");
            user.setOpenid(openid);
            user.setUseStatus(BiuUserEntity.USER_AVAIL_STATUS);
            user.setUnionid(unionid != null ? unionid : "");
            user.setCommentStatus(BiuUserEntity.COMMUNICATE_OPEN_STATUS);
            user.setLastIp(HttpTool.getIpAddr(request));
            user.setLastLogin(new Date());
            user = biuDbFactory.getUserDbFactory().getBiuUserImpl().insert(user);
            user.setUserCardno("by" + TimeTool.formatDate(new Date(), "yyMM") + hashTool.getHashids(4, "0123456789abcdefghjkmnpqrstuvwxyz").encode(user.getId()));
            biuDbFactory.getUserDbFactory().getBiuUserImpl().update(user);
            if (user.getId() > 0) {
                result.put("need_info", "1");
            }
            biuRedisFactory.getBiuRedisTool().getValueOperator().set(openidKey, user.getId());
            userService.addUserMessage(0, user.getId(), BiuMessageEntity.PUBLIC_NOTICE, 0, "欢迎加入BIU笔友", "", SystemOption.REGISTER_NOTICE_BANNER.getValue(), Arrays.asList(SystemOption.REGISTER_NOTICE_IMAGE.getValue()).stream().collect(Collectors.toList()));
            userService.addUserMessage(0, user.getId(), BiuMessageEntity.PUBLIC_NOTICE, 0, "平台注意事项", "", SystemOption.REGISTER_NOTICE_PLAT_BANNER.getValue(), Arrays.asList(SystemOption.REGISTER_NOTICE_PLAT_IMAGE.getValue()).stream().collect(Collectors.toList()));
        } else {
            result.put("is_penuser", user.getIsPenuser());
        }
        // 更新排序时间
        userService.setUserSortTime(user.getId());
        if (user.getId() > 0) {
            Map<String, String> data = new HashMap<>();
            data.put("user_id", String.valueOf(user.getId()));
            String token = new JwtTool().createToken("biu", "tree", data);
            biuRedisFactory.getBiuRedisTool().getValueOperator().set(token + ":" + user.getId(), token, 86400);
            result.put("token", token);
        } else {
            result.put("token", "");
            result.put("need_info", "0");
        }
        userService.syncUserIndex(user.getId());
        return new FuncResult(true, "", result);
    }
}
