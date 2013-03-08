package com.nwm.coauthor.exception;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class BadRequestException extends BaseException {
	private static final long serialVersionUID = 1L;

	public BadRequestException(Map<String, String> batchErrors){
		setId(ExceptionMapper.BAD_REQUEST_EXCEPTION);
		setDescription("You made a bad request.");
		setBatchErrors(batchErrors);
		setHttpStatus(HttpStatus.BAD_REQUEST);
	}
	
	public BadRequestException(BaseException e){
		setId(e.getId());
		setDescription(e.getDescription());
		setHttpStatus(e.getHttpStatus());
		setBatchErrors(e.getBatchErrors());
		setThreadId(e.getThreadId());
	}
	
	@Override
	public String toString(){
		Set<Entry<String, String>> entries = null;
		
		if(getBatchErrors() != null){
			entries = getBatchErrors().entrySet();
		}
		
		String batchErrors = "";
		for(Entry<String, String> entry : entries){
			batchErrors += entry.getKey() + ":" + entry.getValue() + " ";
		}
		
		return "id: " + getId() + " description: " + getDescription() + " httpStatus " + getHttpStatus() + " errors: " + batchErrors;
	}
}
