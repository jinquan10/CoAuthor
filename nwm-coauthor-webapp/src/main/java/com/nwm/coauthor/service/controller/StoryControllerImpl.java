package com.nwm.coauthor.service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.CreateStoryBadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.manager.StoryManagerImpl;
import com.nwm.coauthor.service.model.StoryEntryModel;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.GetPrivateStoriesResponseWrapper;

@Controller
@RequestMapping(value = "/story", produces = "application/json", consumes = "application/json")
public class StoryControllerImpl extends BaseControllerImpl implements StoryController{
	@Autowired
	private AuthenticationManagerImpl authenticationManager;
	
	@Autowired
	private StoryManagerImpl storyManager;
	
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createStory(@RequestHeader("Authorization") String coToken, @RequestBody CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException {
		validateCreateStoryRequest(createStoryRequest);
		
		String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
		storyManager.createStory(createStoryModelFromRequest(fbId, createStoryRequest));
		
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}

	@Override
	@RequestMapping(value = "/private", method = RequestMethod.GET)
	public ResponseEntity<GetPrivateStoriesResponseWrapper> getPrivateStories(String coToken) throws AuthenticationUnauthorizedException {
		String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
		
		GetPrivateStoriesResponseWrapper wrapper = new GetPrivateStoriesResponseWrapper(storyManager.getStoriesByFbId(fbId));
		
		return new ResponseEntity<GetPrivateStoriesResponseWrapper>(wrapper, HttpStatus.OK);
	}

	protected StoryModel createStoryModelFromRequest(String fbId, CreateStoryRequest request){
		List<StoryEntryModel> entries = new ArrayList<StoryEntryModel>();
		entries.add(new StoryEntryModel(fbId, request.getEntry()));
		
		StoryModel model = new StoryModel();
		model.setEntries(entries);
		model.setFbFriends(request.getFbFriends());
		model.setIsPublished(false);
		model.setLastFriendEntry(fbId);
		model.setLeaderFbId(fbId);
		model.setNumCharacters(request.getNumCharacters());
		model.setTitle(request.getTitle());
		
		return model;
	}
	
	protected void validateCreateStoryRequest(CreateStoryRequest createStoryRequest) throws CreateStoryBadRequestException{
		boolean isError = false;
		Map<String, String> batchErrors = new HashMap<String, String>();  
		
		if(createStoryRequest.getNumCharacters() == null || createStoryRequest.getNumCharacters() < 1){
			batchErrors.put("numCharacters", "There must be at least 1 character per entry.");
			isError = true;
		}else if(createStoryRequest.getNumCharacters() > 1000){
			batchErrors.put("numCharacters", "There must be less than 1000 characters per entry.");
			isError = true;
		}
		
		if(createStoryRequest.getEntry() == null){
			batchErrors.put("entry", "You must fill out an entry.");
			isError = true;
		}else if(createStoryRequest.getEntry().length() < 1){
			batchErrors.put("entry", "Your entry must be at least 1 character long.");
			isError = true;
		}else if(createStoryRequest.getEntry().length() > createStoryRequest.getNumCharacters()){
			batchErrors.put("entry", "Your entry exceeds the number of characters specified.");
			isError = true;
		}
		
		if(createStoryRequest.getFbFriends() == null || createStoryRequest.getFbFriends().size() < 1){
			batchErrors.put("fbFriends", "You must include at least one Facebook friend.");
			isError = true;
		}
		
		if(StringUtils.hasText(createStoryRequest.getTitle())){
			if(createStoryRequest.getTitle().length() > 100){
				batchErrors.put("title", "The length of your title must not exceed 100.");
				isError = true;
			}
		}
		
		if(isError){
			throw new CreateStoryBadRequestException(batchErrors);
		}
	}
}
