package com.zuosuo.treehole.processor;

import com.zuosuo.biudb.entity.BiuUserEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.HttpTool;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.component.tool.StringTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.tool.JwtTool;
import com.zuosuo.wechat.SessionInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class WechatUserLoginProcessor {

    public FuncResult run(HttpServletRequest request, SessionInfo session, BiuDbFactory dbFactory) {
        if (session.getOpenid().isEmpty()) {
            return new FuncResult();
        }
        Map<String, Object> result = new HashMap<>();
        String openid = session.getOpenid();
        String unionid = session.getUnionid();
        ProviderOption option = new ProviderOption();
        option.addCondition("openid", openid);
        BiuUserEntity user = dbFactory.getUserDbFactory().getBiuUserImpl().single(option);
        if (user == null) {
            user = new BiuUserEntity();
            user.setUsername(String.valueOf(TimeTool.getCurrentTimestamp() / 1000) + StringTool.random(5));
            user.setOpenid(openid);
            user.setUnionid(unionid != null ? unionid : "");
            user.setLastIp(HttpTool.getIpAddr(request));
            user.setLastLogin(new Date());
            user = dbFactory.getUserDbFactory().getBiuUserImpl().insert(user);
            if (user.getId() > 0) {
                result.put("need_info", "1");
            }
        }
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
