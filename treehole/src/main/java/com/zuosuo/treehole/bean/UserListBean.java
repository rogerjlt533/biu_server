package com.zuosuo.treehole.bean;

public class UserListBean extends BaseVerifyBean {

    private int custom, communicate, sex;
    private String age;

    public int getCustom() {
        return custom;
    }

    public void setCustom(int custom) {
        this.custom = custom > 0 ? custom : 0;
    }

    public int getCommunicate() {
        return communicate;
    }

    public void setCommunicate(int communicate) {
        this.communicate = communicate > 0 ? communicate : 0;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex > 0 ? sex : 0;
    }

    public int[] getAge() {
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
        if (age != null && !age.trim().isEmpty()) {
            this.age = age.trim();
        } else{
            this.age = "";
        }
    }

    @Override
    public VerifyResult verify() {
        if (custom > 1 || custom < 0) {
            return new VerifyResult("推荐模式参数错误");
        }
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
