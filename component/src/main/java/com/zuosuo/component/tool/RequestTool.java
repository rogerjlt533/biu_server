package com.zuosuo.component.tool;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;

public class RequestTool {

    public static List<String> getParamList(HttpServletRequest request, String name) {
        List<String> result = new ArrayList<>();
        Enumeration<String> paramNames = request.getParameterNames();
        Map<Integer, String> paramTree = new TreeMap<>();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.startsWith(name + "[")) {
                int paramIndex = Integer.valueOf(paramName.replace(name + "[", "").replace("]", ""));
                String[] paramValue = request.getParameterValues(paramName);
                if (paramValue.length > 0) {
                    paramTree.put(paramIndex, paramValue[0]);
                }
            }
        }
        List<Map.Entry<Integer, String>> list = new ArrayList<>(paramTree.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        list.forEach(item -> result.add(item.getValue()));
        return result;
    }

    public static Map getParamMap(HttpServletRequest request, String name) {
        Map<String, String> result = new HashMap();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.startsWith(name + "[")) {
                String[] paramValue = request.getParameterValues(paramName);
                if (paramValue.length > 0) {
                    String paramKey = paramName.replace(name + "[", "");
                    paramKey = paramKey.replace("]", "");
                    result.put(paramKey, paramValue[0]);
                }
            }
        }
        return result;
    }

    public static String getString(HttpServletRequest request, String name, String defaultValue) {
        return request.getParameter(name) != null ? request.getParameter(name) : defaultValue;
    }

    public static double getDouble(HttpServletRequest request, String name, double defaultValue) {
        return getValue(request, name, (item) -> Double.parseDouble(item), defaultValue);
    }

    public static long getLong(HttpServletRequest request, String name, long defaultValue) {
        return getValue(request, name, (item) -> Long.parseLong(item), defaultValue);
    }

    public static int getInt(HttpServletRequest request, String name, int defaultValue) {
        return getValue(request, name, (item) -> Integer.parseInt(item), defaultValue);
    }

    public static <T> T getValue(HttpServletRequest request, String name, Function<String, T> func, T defaultValue) {
        try {
            return func.apply(request.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
