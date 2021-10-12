package com.zuosuo.component.tool;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonTool {

    public static String toJson(Object object) {
        return JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue);
    }

    public static <T> T parse(String json, Class<T> className) {
        try {
            return JSONObject.parseObject(json, className);
        } catch (JSONException exception) {
            return null;
        }
    }
}
