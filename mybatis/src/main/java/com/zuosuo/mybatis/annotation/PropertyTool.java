package com.zuosuo.mybatis.annotation;


import com.zuosuo.mybatis.entity.BaseEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyTool {

    public static List<PropertyOption> getPropertyList(BaseEntity entity) {
        List<PropertyOption> propertyList = new ArrayList<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field: fields) {
                if (field.isAnnotationPresent(EntityProperty.class)) {
                    PropertyOption option = new PropertyOption(field.getName(), field.getType());
                    EntityProperty property = field.getAnnotation(EntityProperty.class);
                    if (!property.column().isEmpty()) {
                        option.setColumn(property.column());
                    } else {
                        option.setColumn(humpToLine(field.getName()));
                    }
                    option.setComment(property.comment());
                    propertyList.add(option);
                }
            }
        }
        return propertyList;
    }

    /**
     * 驼峰转下划线
     *
     * @param value
     * @return
     */
    public static String humpToLine(String value) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void setPropertyByField(BaseEntity entity, String name, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            System.out.println("set:" + e.getMessage());
        }
    }

    public static <T> T getPropertyByField(BaseEntity entity, String name, T defaultValue, Class<T> className) {
        try {
            Field field = entity.getClass().getDeclaredField(name);
            field.setAccessible(true);
            if (field.get(entity) == null) {
                return defaultValue;
            }
            return (T) field.get(entity);
        } catch (Exception e) {
            System.out.println("get:" + e.getMessage());
        }
        return defaultValue;
    }

}
