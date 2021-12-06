package com.zuosuo.treehole.result;

public class FriendCommunicateInfo {
    private int id = 0;
    private String name;

    public FriendCommunicateInfo() {
        name = "";
    }

    public FriendCommunicateInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name != null? name.trim(): "";
    }

    public void setName(String name) {
        this.name = name;
    }
}
