package com.zuosuo.auth.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTResult {
    private String issuer;
    private String subject;
    private List<String> audience;
    private Date issueAt;
    private Date expireAt;
    private Map<String, String> data;

    public JWTResult() {
        issuer = "";
        subject = "";
        data = new HashMap<>();
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getAudience() {
        return audience;
    }

    public void setAudience(List<String> audience) {
        this.audience = audience;
    }

    public Date getIssueAt() {
        return issueAt;
    }

    public void setIssueAt(Date issueAt) {
        this.issueAt = issueAt;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getData(String name) {
        return data.get(name);
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public void setData(String name, String value) {
        data.put(name, value);
    }
}
