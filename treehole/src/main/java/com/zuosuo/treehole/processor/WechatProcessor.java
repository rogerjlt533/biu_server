package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.HttpTool;
import com.zuosuo.component.tool.StringTool;
import com.zuosuo.treehole.config.MiniWechatConfig;
import com.zuosuo.treehole.service.UserService;
import com.zuosuo.treehole.tool.JwtTool;
import com.zuosuo.wechat.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class WechatProcessor {

    @Autowired
    private MiniWechatConfig miniWechatConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private BiuDbFactory biuDbFactory;

    public MiniWechatConfig getMiniWechatConfig() {
        return miniWechatConfig;
    }

    public FuncResult loginCode(HttpServletRequest request, SessionInfo session) {
        if (session.getOpenid().isEmpty()) {
            return new FuncResult();
        }
        Map<String, Object> result = new HashMap<>();
        String openid = session.getOpenid();
        String unionid = session.getUnionid();
        BiuUserEntity user = userService.getUserByOpenid(openid);
        if (user == null) {
            user = new BiuUserEntity();
            user.setUsername("");
            user.setOpenid(openid);
            user.setUseStatus(BiuUserEntity.USER_AVAIL_STATUS);
            user.setUnionid(unionid != null ? unionid : "");
            user.setLastIp(HttpTool.getIpAddr(request));
            user.setLastLogin(new Date());
            user = biuDbFactory.getUserDbFactory().getBiuUserImpl().insert(user);
            if (user.getId() > 0) {
                result.put("need_info", "1");
            }
        }
        // 更新排序时间
        userService.setUserSortTime(user.getId());
        if (user.getId() > 0) {
            Map<String, String> data = new HashMap<>();
            data.put("user_id", String.valueOf(user.getId()));
            result.put("token", new JwtTool().createToken("biu", "tree", data));
        } else {
            result.put("token", "");
            result.put("need_info", "0");
        }
        return new FuncResult(true, "", result);
    }
}
