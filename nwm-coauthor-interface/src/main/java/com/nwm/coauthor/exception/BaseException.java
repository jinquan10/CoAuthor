package com.nwm.coauthor.exception;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include = Inclusion.NON_NULL)
@JsonAutoDetect(creatorVisibility=Visibility.NONE, fieldVisibility=Visibility.NONE, getterVisibility=Visibility.NONE, isGetterVisibility=Visibility.NONE, setterVisibility=Visibility.NONE)
public class BaseException extends Exception{
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private ExceptionMapper id;

	@JsonProperty("description")
	private String description;
	
	@JsonProperty("statusCode")
	private int statusCode;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public ExceptionMapper getId() {
		return id;
	}

	public void setId(ExceptionMapper id) {
		this.id = id;
	}
	
}
