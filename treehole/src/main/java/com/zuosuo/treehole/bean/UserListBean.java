package com.zuosuo.treehole.bean;

public class UserListBean extends BaseVerifyBean {

    public static final int ZONGHE = 0;
    public static final int RECOMMEND = 1;

    private int communicate = 0, sex = 0, method = 0;
    private String age = "", last = "";

    public int getCommunicate() {
        return communicate = communicate > 0 ? communicate : 0;
    }

    public void setCommunicate(int communicate) {
        this.communicate = communicate;
    }

    public int getSex() {
        return sex > 0 ? sex : 0;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int[] getAge() {
        if (age == null) {
            return null;
        }
        if (age.isEmpty()) {
            return null;
        }
        String[] ages = age.split(",");
        if (ages.length != 2) {
            return null;
        }
        int[] range = new int[2];
        for (int i = 0; i < ages.length; i++) {
            range[i] = Integer.parseInt(ages[i]);
        }
        return range;
    }

    public void setAge(String age) {
        this.age = age != null ? age.trim() : "";
    }

    public String getLast() {
        return last == null ? "" : last.trim();
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public VerifyResult verify() {
        if (sex > 2 || sex < 0) {
            return new VerifyResult("性别参数错误");
        }
        if (communicate > 2 || communicate < 0) {
            return new VerifyResult("通讯方式参数错误");
        }
        return new VerifyResult();
    }
}
