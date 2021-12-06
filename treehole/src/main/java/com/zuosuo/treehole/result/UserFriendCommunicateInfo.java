package com.zuosuo.treehole.result;

import java.util.HashMap;
import java.util.Map;

public class UserFriendCommunicateInfo {
    private int received = 0, communicate = 0;
    private String label = "", sendTag = "", receiveTag = "", logTime = "";
    private Map<String, String> info;

    public UserFriendCommunicateInfo() {
        info = new HashMap<>();
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public int getCommunicate() {
        return communicate;
    }

    public void setCommunicate(int communicate) {
        this.communicate = communicate;
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

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }
}
