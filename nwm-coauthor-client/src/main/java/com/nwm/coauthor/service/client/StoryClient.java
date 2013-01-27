package com.nwm.coauthor.service.client;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.UrlPathHelper;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.mapping.ExceptionMapperWrapper;
import com.nwm.coauthor.service.controller.StoryController;
import com.nwm.coauthor.service.resource.request.AddEntryRequest;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.AddEntryResponse;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoriesResponseWrapper;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

public class StoryClient extends BaseClient implements StoryController{
	private static final String CREATE_STORY_ENDPOINT = "/story";
	private static final String GET_PRIVATE_STORIES_ENDPOINT = "/story/privates";
	private static final String ADD_ENTRY_ENDPOINT = "/story/entry";
	private static final String GET_PRIVATE_STORY_ENDPOINT = "/story/private/";
	private static final String LIKE_ENDPOINT = "/story/like/";

	public StoryClient(){
		
	}
	
	@Override
	public ResponseEntity<CreateStoryResponse> createStory(String coToken, CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
		try{
			return restTemplate.exchange(urlResolver(CREATE_STORY_ENDPOINT), HttpMethod.POST, httpEntity(createStoryRequest, coToken), CreateStoryResponse.class);
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
	}

	@Override
	public ResponseEntity<PrivateStoriesResponseWrapper> getPrivateStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException{
		try{
			return restTemplate.exchange(urlResolver(GET_PRIVATE_STORIES_ENDPOINT), HttpMethod.GET, httpEntity(null, coToken), PrivateStoriesResponseWrapper.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			}else{
				throw new SomethingWentWrongException();
			}
		}
	}

	@Override
	public ResponseEntity<AddEntryResponse> addEntry(String coToken, AddEntryRequest entry) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException {
		try{
			return restTemplate.exchange(urlResolver(ADD_ENTRY_ENDPOINT), HttpMethod.POST, httpEntity(entry, coToken), AddEntryResponse.class);
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

	@Override
	public ResponseEntity<PrivateStoryResponse> getPrivateStory(String coToken, String storyId) throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException {
		try{
			return restTemplate.exchange(urlResolver(GET_PRIVATE_STORY_ENDPOINT) + storyId, HttpMethod.GET, httpEntity(null, coToken), PrivateStoryResponse.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			} else if(emw.getClazz() == BadRequestException.class){
				throw new BadRequestException(emw.getBaseException());
			} else if(emw.getClazz() == StoryNotFoundException.class){
				throw new StoryNotFoundException();
			} else{
				throw new SomethingWentWrongException();
			}
		}
	}

	@Override
	public void like(String coToken, String storyId) {
		restTemplate.exchange(urlResolver(LIKE_ENDPOINT) + storyId, HttpMethod.POST, httpEntity(null, coToken), String.class);
	}
}
