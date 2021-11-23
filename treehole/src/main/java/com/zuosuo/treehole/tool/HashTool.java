package com.zuosuo.treehole.tool;

import com.zuosuo.component.tool.HashidsTool;
import com.zuosuo.treehole.config.SystemOption;
import org.springframework.stereotype.Component;

@Component("HashTool")
public class HashTool {

    public HashidsTool getHashids() {
        return new HashidsTool(SystemOption.APP_KEY.getValue());
    }

    public HashidsTool getHashids(String salt) {
        return new HashidsTool(salt);
    }

    public HashidsTool getHashids(String salt, int minHashLength) {
        return new HashidsTool(salt, minHashLength);
    }

    public HashidsTool getHashids(int minHashLength) {
        return new HashidsTool(SystemOption.APP_KEY.getValue(), minHashLength);
    }

    public HashidsTool getHashids(int minHashLength, String alphabet) {
        return new HashidsTool(SystemOption.APP_KEY.getValue(), minHashLength, alphabet);
    }
}
