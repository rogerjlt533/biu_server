package com.zuosuo.component.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.function.Function;

public class SessionTool {
    private HttpServletRequest request;

    public SessionTool(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public String sessionId() {
        return getSession().getId();
    }

    public void set(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    public void setJson(String name, Object value) {
        getSession().setAttribute(name, JsonTool.toJson(value));
    }

    public Object get(String name) {
        return getSession().getAttribute(name);
    }

    public String getString(String name) {
        return String.valueOf(get(name));
    }

    public <T> T getNumber(String name, Function<String, T> convert, Function<Number, T> transform) {
        try {
            return convert.apply(getString(name));
        } catch (NumberFormatException e) {
            Object value = get(name);
            if (value instanceof Long || value instanceof Integer || value instanceof Float || value instanceof Double) {
                return transform.apply((Number) value);
            }
        }
        return convert.apply("0");
    }

    public long getLong(String name) {
        return getNumber(name, Long::parseLong, (Number number) -> number.longValue());
    }

    public int getInteger(String name) {
        return getNumber(name, Integer::parseInt, (Number number) -> number.intValue());
    }

    public float getFloat(String name) {
        return getNumber(name, Float::parseFloat, (Number number) -> number.floatValue());
    }

    public double getDouble(String name) {
        return getNumber(name, Double::parseDouble, (Number number) -> number.doubleValue());
    }

    public <T> T getJson(String name, Class<T> clazz) {
        return JsonTool.parse(getString(name), clazz);
    }

    public void remove(String name) {
        getSession().removeAttribute(name);
    }
}
