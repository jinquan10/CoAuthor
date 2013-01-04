package com.nwm.coauthor.service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.mapping.ExceptionMapper;
import com.nwm.coauthor.service.controller.StoryController;
import com.nwm.coauthor.service.resource.request.AddEntryRequest;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.PrivateStoriesResponseWrapper;

public class StoryClient extends BaseClient implements StoryController{
	private static final String CREATE_STORY_ENDPOINT = "/story";
	private static final String GET_PRIVATE_STORIES_ENDPOINT = "/story/private";
	private static final String ADD_ENTRY_ENDPOINT = "/story/entry/";

	@Override
	public ResponseEntity<String> createStory(String coToken, CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
		ResponseEntity<String> response = null;
		
		try{
			response = restTemplate.exchange(urlResolver(CREATE_STORY_ENDPOINT), HttpMethod.POST, httpEntity(createStoryRequest, coToken), String.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapper em = convertToExceptionMapper(e);
			
			if(em.getClazz() == SomethingWentWrongException.class){
				throw new SomethingWentWrongException();
			}else if(em.getClazz() == BadRequestException.class){
				throw new BadRequestException(em.getBaseException());
			}else{
				throw new AuthenticationUnauthorizedException();
			}
		}
		
		return response;
	}

	@Override
	public ResponseEntity<PrivateStoriesResponseWrapper> getPrivateStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException{
		ResponseEntity<PrivateStoriesResponseWrapper> response = null;
		
		try{
			response = restTemplate.exchange(urlResolver(GET_PRIVATE_STORIES_ENDPOINT), HttpMethod.GET, httpEntity(null, coToken), PrivateStoriesResponseWrapper.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapper em = convertToExceptionMapper(e);
			
			if(em.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			}else{
				throw new SomethingWentWrongException();
			}
		}
		
		return response;
	}

	@Override
	public void addEntry(String coToken, AddEntryRequest entry) throws SomethingWentWrongException {
		restTemplate.exchange(urlResolver(ADD_ENTRY_ENDPOINT) + entry.getStoryId(), HttpMethod.POST, httpEntity(entry, coToken), String.class);
	}
}
