package com.nwm.coauthor.service.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JsonSchemaField {
    private String displayName;
    private Integer displayOrder;
    private Boolean required;
    private Boolean readOnly;
    private JsonType type;
    private String dateFormat;
    private String format;
    private String formatErrorMessage;
    private List<JsonSelect> selectOptions;

    public Boolean getReadOnly() {
        return readOnly;
    }
    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }
    public Boolean getRequired() {
        return required;
    }
    public void setRequired(Boolean required) {
        this.required = required;
    }
    public JsonType getType() {
        return type;
    }
    public void setType(JsonType type) {
        this.type = type;
    }
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    public List<JsonSelect> getSelectOptions() {
        return selectOptions;
    }
    public void setSelectOptions(List<JsonSelect> selectOptions) {
        this.selectOptions = selectOptions;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getDateFormat() {
        return dateFormat;
    }
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public String getFormatErrorMessage() {
        return formatErrorMessage;
    }
    public void setFormatErrorMessage(String formatErrorMessage) {
        this.formatErrorMessage = formatErrorMessage;
    }
}
