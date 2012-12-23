package com.nwm.coauthor.service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.CreateStoryBadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;

@Controller
@RequestMapping(value = "/story", produces = "application/json", consumes = "application/json")
public class StoryControllerImpl extends BaseControllerImpl implements StoryController{
	@Autowired
	private AuthenticationManagerImpl authenticationManager;
	
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createStory(@RequestHeader("Authorization") String coToken, @RequestBody CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException {
		String userId = authenticationManager.authenticateCOToken(coToken);
		
		if(createStoryRequest == null){
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}
		
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	protected void validateCreateStoryRequest(CreateStoryRequest createStoryRequest){
		boolean isError = false;
		Map<String, String> batchErrors = new HashMap<String, String>();  
		
		if(createStoryRequest.getNumCharacters() == null || createStoryRequest.getNumCharacters() < 1){
			batchErrors.put("numCharacters", "There must be at least 1 character per entry.");
			isError = true;
		}else if(createStoryRequest.getNumCharacters() > 1000){
			batchErrors.put("numCharacters", "There must be less than 1000 characters per entry.");
			isError = true;
		}
	}
}
