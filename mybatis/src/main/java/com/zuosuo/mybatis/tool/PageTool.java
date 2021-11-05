package com.zuosuo.mybatis.tool;

public class PageTool {

    public static long parsePage(long page) {
        return page > 0 ? page : 1;
    }

    public static String getLimit(long page, int size) {
        long offset = (page - 1) * size;
        return "limit " + offset + "," + size;
    }
}
