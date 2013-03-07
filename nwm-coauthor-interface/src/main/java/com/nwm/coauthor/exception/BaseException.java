package com.nwm.coauthor.exception;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.http.HttpStatus;

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
	
	private HttpStatus httpStatus;

	@JsonProperty("batchErrors")
	private Map<String, String> batchErrors;
	
	@JsonProperty("threadId")
	private String threadId;
	
	public Map<String, String> getBatchErrors() {
		return batchErrors;
	}

	public void setBatchErrors(Map<String, String> batchErrors) {
		this.batchErrors = batchErrors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ExceptionMapper getId() {
		return id;
	}

	public void setId(ExceptionMapper id) {
		this.id = id;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
}
