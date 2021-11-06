package com.zuosuo.treehole.bean;

public class UserListBean extends BaseVerifyBean {

    private int communicate = 0, sex = 0;
    private String age = "";

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
        this.age = age.trim();
    }

    @Override
    public VerifyResult verify() {
        if (sex > 2 || sex < 0) {
            return new VerifyResult("性别参数错误");
        }
        if (communicate > 2 || communicate < 0) {
            return new VerifyResult("通讯方式参数错误");
        }
        if (getAge() == null) {
            return new VerifyResult("年龄参数错误");
        }
        return new VerifyResult();
    }
}
