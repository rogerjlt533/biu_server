package com.zuosuo.component.phprpc;

import java.lang.reflect.InvocationTargetException;

public class SerializerTool {

    public static Serializer getSerializer() {
        return new Serializer();
    }

    public static String serializer(Object value) {
        try {
            return new String(getSerializer().serialize(value));
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return "";
    }

    public static Object unserialize(String value) {
        try {
            return getSerializer().unserialize(value.getBytes());
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return null;
    }

    public static <T> T unserialize(String value, Class<T> clazz) {
        try {
            return (T) getSerializer().unserialize(value.getBytes(), clazz);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return null;
    }
}
