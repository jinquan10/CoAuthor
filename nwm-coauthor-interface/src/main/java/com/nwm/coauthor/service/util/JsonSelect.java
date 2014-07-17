package com.nwm.coauthor.service.util;

public class JsonSelect {
    private String displayValue;
    private Object value;

    public static JsonSelect create(String displayValue, Object value) {
        JsonSelect s = new JsonSelect();
        s.setDisplayValue(displayValue);
        s.setValue(value);

        return s;
    }

    public String getDisplayValue() {
        return displayValue;
    }
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
