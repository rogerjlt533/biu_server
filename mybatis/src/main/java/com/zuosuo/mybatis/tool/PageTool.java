package com.zuosuo.mybatis.tool;

public class PageTool {

    public static final int DEFAULT_SIZE = 10;

    public static long parsePage(long page) {
        return page > 0 ? page : 1;
    }

    public static long getOffset(long page, int size) {
        return (parsePage(page) - 1) * size;
    }

    public static String getLimit(long page, int size) {
        return "limit " + getOffset(page, size) + "," + size;
    }
}
