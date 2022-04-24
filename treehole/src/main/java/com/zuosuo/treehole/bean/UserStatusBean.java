package com.zuosuo.treehole.bean;

import java.util.ArrayList;
import java.util.List;

public class UserStatusBean extends BaseVerifyBean {

    public static List<String> TYPE = new ArrayList<String>(){{ add("comment"); add("search"); add("private_msg"); }};

    private String type;
    private int status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public VerifyResult verify() {
        if (type == null) {
            return new VerifyResult("请选择类别参数");
        }
        if (!TYPE.contains(type)) {
            return new VerifyResult("类别参数错误");
        }
        if (status > 2 || status < 0) {
            return new VerifyResult("状态参数错误");
        }
        return new VerifyResult();
    }
}
