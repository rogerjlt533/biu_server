package com.zuosuo.treehole.result;

public class UserFriendCommunicateInfo {
    private int received = 0;
    private String id = "", name = "", phone = "", address = "", email = "", label = "", sendTag = "", receiveTag = "", logTime = "";

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name != null? name.trim(): "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone != null? phone.trim(): "";
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address != null? address.trim(): "";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email != null? email.trim(): "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSendTag() {
        return sendTag;
    }

    public void setSendTag(String sendTag) {
        this.sendTag = sendTag;
    }

    public String getReceiveTag() {
        return receiveTag;
    }

    public void setReceiveTag(String receiveTag) {
        this.receiveTag = receiveTag;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
}
