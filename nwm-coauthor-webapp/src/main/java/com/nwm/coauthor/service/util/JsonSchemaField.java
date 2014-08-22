package com.nwm.coauthor.service.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JsonSchemaField {
    private String inputType;
    private String inputIcon;
    private String displayName;
    private Integer displayOrder;
    private Boolean required;
    private Boolean readOnly;
    private JsonType type;
    private String dateFormat;
    private String format;
    private String formatErrorMessage;
    private Integer minLength;
    private Integer maxLength;
    private String hintText;
    private List<JsonSelect> selectOptions;

    public String getHintText() {
		return hintText;
	}
	public void setHintText(String hintText) {
		this.hintText = hintText;
	}
	public Integer getMinLength() {
		return minLength;
	}
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
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
    public String getInputIcon() {
        return inputIcon;
    }
    public void setInputIcon(String inputIcon) {
        this.inputIcon = inputIcon;
    }
    public String getInputType() {
        return inputType;
    }
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
}
