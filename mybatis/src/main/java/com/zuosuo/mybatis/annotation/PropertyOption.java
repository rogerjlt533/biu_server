package com.zuosuo.mybatis.annotation;

import java.lang.reflect.Field;

public class PropertyOption {
    private String column = "";
    private String property;
    private String comment = "";
    private Class typeClass;

    public PropertyOption(String property, Class typeClass) {
        this.property = property;
        this.typeClass = typeClass;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(Class typeClass) {
        this.typeClass = typeClass;
    }

    public <R> R getValueByField(String fieldName, Class<R> className) {
        try {
            Field field = this.getClass().getField(fieldName);
            return (R) field.get(className);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
