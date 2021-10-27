package com.zuosuo.treehole.tool;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zuosuo.auth.jwt.BaseJwtTool;
import com.zuosuo.auth.jwt.JWTResult;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.tool.JsonTool;
import com.zuosuo.treehole.config.SystemOption;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTool extends BaseJwtTool {

    @Override
    public String createToken(String issuer, String subject, String... audience) {
        return createToken(SystemOption.APP_KEY.getValue(), issuer, subject, new DiscTime().setDay(SystemOption.TOKEN_DAYS.getInteger()), audience);
    }

    @Override
    public String createToken(String issuer, String subject, Map<String, String> claims, String... audience) {
        return createToken(SystemOption.APP_KEY.getValue(), issuer, subject, new DiscTime().setDay(SystemOption.TOKEN_DAYS.getInteger()), claims, audience);
    }

    @Override
    public JWTResult getResult(String token) {
        return getResult(token, SystemOption.APP_KEY.getValue());
    }

    @Override
    public JWTResult getResult(DecodedJWT jwt) {
        JWTResult result = super.getResult(jwt);
        HashMap<String, String> custom = (HashMap<String, String>) getCustomPayload(jwt, (json) -> {
            HashMap<String, String> res = new HashMap<>();
            HashMap<String, Object> data = (HashMap<String, Object>) JsonTool.parse(json, Map.class);
            res.put("user_id", data.containsKey("user_id")? String.valueOf(data.get("user_id")): "0");
            return res;
        });
        result.setData("user_id", custom.get("user_id"));
        return result;
    }
}
