package com.nwm.coauthor.exception;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class CreateStoryBadRequestException extends BaseException {
	private static final long serialVersionUID = 1L;

	public CreateStoryBadRequestException(){
		setId(ExceptionMapper.CREATE_STORY_BAD_REQUEST_EXCEPTION);
		setDescription("You have errors in your story creation.");
		setStatusCode(400);
	}
	
	public CreateStoryBadRequestException(Map<String, String> batchErrors){
		setId(ExceptionMapper.CREATE_STORY_BAD_REQUEST_EXCEPTION);
		setDescription("You have errors in your story creation.");
		setBatchErrors(batchErrors);
		setStatusCode(400);
	}
	
	public CreateStoryBadRequestException(BaseException e){
		setId(e.getId());
		setDescription(e.getDescription());
		setStatusCode(e.getStatusCode());
		setBatchErrors(e.getBatchErrors());
	}
	
	@Override
	public String toString(){
		Set<Entry<String, String>> entries = getBatchErrors().entrySet();
		
		String batchErrors = "";
		for(Entry<String, String> entry : entries){
			batchErrors += entry.getKey() + ":" + entry.getValue() + " ";
		}
		
		return "id: " + getId() + " description: " + getDescription() + " statusCode " + getStatusCode() + " errors: " + batchErrors;
	}
}
