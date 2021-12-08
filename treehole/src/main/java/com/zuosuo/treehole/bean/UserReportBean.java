package com.zuosuo.treehole.bean;

import com.zuosuo.biudb.entity.BiuUserReportEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserReportBean extends BaseVerifyBean {

    private int type;
    private String relate;
    private String content, images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRelate() {
        return relate != null ? relate.trim() : "";
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    public String getContent() {
        return content != null ? content.trim() : "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        String images = this.images == null ? "" : this.images.replaceAll(" ", "");
        if (images.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(images.trim().split(","));
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public VerifyResult verify() {
        if (getType() <= 0) {
            return new VerifyResult("请选择类型");
        }
        if (getType() != BiuUserReportEntity.REPORT_TYPE_REPORT && getContent().isEmpty()) {
            return new VerifyResult("请填写内容");
        }
        if (getType() == BiuUserReportEntity.REPORT_TYPE_REPORT && getRelate().isEmpty()) {
            return new VerifyResult("请选择投诉人");
        }
        return new VerifyResult();
    }
}
