package com.nwm.coauthor.service.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public enum JsonType {
    text, BOOLEAN, number, OBJECT, selection, date;

    private static List<Class<?>> stringClasses = new ArrayList<Class<?>>();

    static {
        stringClasses.add(String.class);
    }

    public static JsonType determineJsonType(Field field) throws Exception {
        if (field.getAnnotation(SelectOptions.class) != null) {
            return JsonType.selection;
        }

        if (field.getAnnotation(DateField.class) != null) {
            return JsonType.date;
        }

        if (field.getType().isPrimitive()) {
            throw new Exception("No Primitives allowed!");
        }

        if (field.getType().equals(Boolean.class)) {
            return BOOLEAN;
        }

        if (field.getType().equals(Integer.class)) {
            return number;
        }

        if (field.getType().getPackage().getName().contains("espn")) {
            return OBJECT;
        }

        return text;
    }
}
