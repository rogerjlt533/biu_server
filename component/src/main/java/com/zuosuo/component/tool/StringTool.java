package com.zuosuo.component.tool;

import org.springframework.util.DigestUtils;

import java.util.Random;

public class StringTool {

    public static String md5(String content) {
        return DigestUtils.md5DigestAsHex(content.getBytes());
    }

    public static String random(int length) {
        String result = "";
        Random r = new Random(1);
        for (int i = 0; i < length; i++) {
            result += r.nextInt(9);
        }
        return result;
    }
}
