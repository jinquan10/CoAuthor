package com.nwm.coauthor.service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.mapping.ExceptionMapperWrapper;
import com.nwm.coauthor.service.controller.StoryController;
import com.nwm.coauthor.service.resource.request.AddEntryRequest;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoriesResponseWrapper;

public class StoryClient extends BaseClient implements StoryController{
	private static final String CREATE_STORY_ENDPOINT = "/story";
	private static final String GET_PRIVATE_STORIES_ENDPOINT = "/story/private";
	private static final String ADD_ENTRY_ENDPOINT = "/story/entry/";

	public StoryClient(){
		
	}
	
	@Override
	public ResponseEntity<CreateStoryResponse> createStory(String coToken, CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
		ResponseEntity<CreateStoryResponse> response = null;
		
		try{
			response = restTemplate.exchange(urlResolver(CREATE_STORY_ENDPOINT), HttpMethod.POST, httpEntity(createStoryRequest, coToken), CreateStoryResponse.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == SomethingWentWrongException.class){
				throw new SomethingWentWrongException();
			}else if(emw.getClazz() == BadRequestException.class){
				throw new BadRequestException(emw.getBaseException());
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
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			}else{
				throw new SomethingWentWrongException();
			}
		}
		
		return response;
	}

	@Override
	public void addEntry(String coToken, AddEntryRequest entry) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException {
		try{
			restTemplate.exchange(urlResolver(ADD_ENTRY_ENDPOINT), HttpMethod.POST, httpEntity(entry, coToken), String.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			} else if(emw.getClazz() == BadRequestException.class){
				throw new BadRequestException(emw.getBaseException());
			} else if(emw.getClazz() == AddEntryException.class){
				throw new AddEntryException();
			} else{
				throw new SomethingWentWrongException();
			}
		}
	}
}
