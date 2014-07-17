package com.nwm.coauthor.service.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonSchemaUtil {
    @Autowired
    private SelectOptionsUtil selectOptionsUtil;

    public <T> T requestBodyForCreationModel(Class<T> modelType, Map<String, Object> requestBody) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
        Map<String, Object> updateFields = extractUpdateFields(requestBody, modelType, true);

        Object model = modelType.newInstance();

        for (Entry<String, Object> entry : updateFields.entrySet()) {
            Field field = model.getClass().getDeclaredField(entry.getKey());
            field.setAccessible(true);

            field.set(model, entry.getValue());
        }

        return (T) model;
    }

    public Map<String, Object> extractUpdateFields(Map<String, Object> requestBody, Class<?> clazz, boolean isCreate) throws NoSuchFieldException, SecurityException {
        Map<String, Object> updateFields = new HashMap<String, Object>();

        for (Entry<String, Object> entry : requestBody.entrySet()) {
            Class<?> currentClass = clazz;

            while(currentClass != null) {
                extractUpdateFieldsCurrentClass(currentClass, updateFields, entry, isCreate);
                currentClass = currentClass.getSuperclass();
            }

        }

        return updateFields;
    }

    private void extractUpdateFieldsCurrentClass(Class<?> clazz, Map<String, Object> updateFields, Entry<String, Object> entry, boolean isCreate) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(entry.getKey());
        } catch (NoSuchFieldException e) {
            return;
        } catch (SecurityException e) {
            return;
        }

        field.setAccessible(true);

        if (!containsAnnotation(field, ReadOnlyField.class) && !containsAnnotation(field, SchemaIgnore.class)) {
            if (!containsAnnotation(field, StringOnUpdate.class) || isCreate) {
                Object convertedObjectType = convertJsonToObject(entry.getValue(), field.getType());
                updateFields.put(entry.getKey(), convertedObjectType);
            } else {
                updateFields.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private Object convertJsonToObject(Object value, Class<?> targetType) {
//        if (targetType.equals(String.class)) {
//            return value;
//        }
//
        if (targetType.equals(Integer.class)) {
            if (value instanceof String) {
                return Integer.parseInt((String) value);
            }
        }

//        if (targetType.equals(Boolean.class)) {
//            return Boolean.parseBoolean((String) value);
//        }

//        if (targetType.getPackage().getName().contains("espn")) {
//            try {
//                return Singletons.objectMapper.readValue(Singletons.objectMapper.writeValueAsString(value), targetType);
//            } catch (JsonParseException e) {
//            } catch (JsonMappingException e) {
//            } catch (JsonProcessingException e) {
//            } catch (IOException e) {
//            }
//        }

        return value;
    }

    public Map<String, Object> outputSchema(Class<?> clazz) throws Exception {
        Map<String, Object> schema = new HashMap<String, Object>();

        Class<?> currentClass = clazz;

        while(currentClass != null) {
            populateSchema(currentClass, schema);
            currentClass = currentClass.getSuperclass();
        }

        return schema;
    }

    private void populateSchema(Class<?> clazz, Map<String, Object> schema) throws Exception {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (!isJsonField(field)) {
                continue;
            }

            if(containsAnnotation(field, SchemaIgnore.class)) {
                continue;
            }

            JsonSchemaField jsonField = new JsonSchemaField();

            if(containsAnnotation(field, DisplayName.class)) {
                jsonField.setDisplayName(field.getAnnotation(DisplayName.class).value());
            } else {
                jsonField.setDisplayName(field.getName());
            }

            if(containsAnnotation(field, RequiredField.class)) {
                jsonField.setRequired(true);
            }

            if(containsAnnotation(field, ReadOnlyField.class)) {
                jsonField.setReadOnly(true);
            }

            if(containsAnnotation(field, Format.class)) {
                jsonField.setFormat(field.getAnnotation(Format.class).value());
                jsonField.setFormatErrorMessage(field.getAnnotation(Format.class).errorMessage());
            }

            if(containsAnnotation(field, DisplayOrder.class)) {
                jsonField.setDisplayOrder(extractDisplayOrder(field));
            } else {
                if (jsonField.getReadOnly() != null && jsonField.getReadOnly()) {
                    jsonField.setDisplayOrder(999);
                } else {
                    jsonField.setDisplayOrder(998);
                }
            }

            if(containsAnnotation(field, SelectOptions.class)) {
                SelectOptions ann = field.getAnnotation(SelectOptions.class);

                jsonField.setSelectOptions((List<JsonSelect>) SelectOptionsUtil.class.getMethod(ann.method()).invoke(selectOptionsUtil));
            }

            if (jsonField.getReadOnly() != null && jsonField.getRequired() != null && jsonField.getReadOnly() && jsonField.getRequired()) {
                throw new Exception("JsonField cannot have both required and readOnly annotations!");
            }

            jsonField.setType(JsonType.determineJsonType(field));

            schema.put(field.getName(), jsonField);
        }
    }

    private boolean isJsonField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            return false;
        }

        return true;
    }

    private boolean containsAnnotation(Field field, Class<?> annotation) {
        for(Annotation ann : field.getDeclaredAnnotations()) {
            if (ann.annotationType().equals(annotation)) {
                return true;
            }
        }

        return false;
    }

    private Integer extractDisplayOrder(Field field) {
        return field.getAnnotation(DisplayOrder.class).value();
    }
}
