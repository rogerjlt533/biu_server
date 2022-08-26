package com.zuosuo.treehole.config;

import com.zuosuo.wechat.TemplateInfo;

public enum TemplateOption implements TemplateInfo {
    DEMO_TEMPLATE("EljBY6FjqxlJo2FSM7RCSM3DSFRgJ6OB9ZuapeM_xww", 1);

    private String id;
    private int type;

    private TemplateOption(String id, int type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }
}
