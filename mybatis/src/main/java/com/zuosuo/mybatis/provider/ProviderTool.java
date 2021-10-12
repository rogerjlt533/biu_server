package com.zuosuo.mybatis.provider;

import java.util.List;

public class ProviderTool {

    public static String normal() {
        return normal("");
    }

    public static String normal(String prefix) {
        return "ISNULL(" + (!prefix.isEmpty() ? prefix + "." : "") + "`deleted_at`)";
    }

    public static String deleted() {
        return "!" + normal();
    }

    public static String deleted(String prefix) {
        return "!" + normal(prefix);
    }

    public static String restore() {
        return restore("");
    }

    public static String restore(String prefix) {
        return (!prefix.isEmpty() ? prefix + "." : "") + "`deleted_at`=NULL";
    }

    public static String softDelete() {
        return softDelete("");
    }

    public static String softDelete(String prefix) {
        return (!prefix.isEmpty() ? prefix + "." : "") + "`deleted_at`=NOW()";
    }

    // 新建时使用
    public static void setTimestamp(List<String> columns, List<String> properties) {
        columns.add("`created_at`");
        properties.add("NOW()");
        columns.add("`updated_at`");
        properties.add("NOW()");
    }
}
