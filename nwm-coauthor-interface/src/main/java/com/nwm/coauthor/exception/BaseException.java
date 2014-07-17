package com.nwm.coauthor.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nwm.coauthor.exception.mapping.ExceptionMapper;
import com.nwm.coauthor.service.resource.response.EntriesResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
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
	
	@JsonProperty("entriesResponse")
	private EntriesResponse entriesResponse;
	
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

    public EntriesResponse getEntriesResponse() {
        return entriesResponse;
    }

    public void setEntriesResponse(EntriesResponse entriesResponse) {
        this.entriesResponse = entriesResponse;
    }
	
}
