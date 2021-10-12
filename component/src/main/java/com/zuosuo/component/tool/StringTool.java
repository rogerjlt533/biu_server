package com.zuosuo.component.tool;

import org.springframework.util.DigestUtils;

public class StringTool {

    public static String md5(String content) {
        return DigestUtils.md5DigestAsHex(content.getBytes());
    }
}
