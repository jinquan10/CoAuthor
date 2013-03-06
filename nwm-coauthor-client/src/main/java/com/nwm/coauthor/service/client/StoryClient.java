package com.nwm.coauthor.service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AddEntryVersionException;
import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnauthorizedException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;
import com.nwm.coauthor.exception.mapping.ExceptionMapperWrapper;
import com.nwm.coauthor.service.controller.StoryController;
import com.nwm.coauthor.service.resource.request.AddEntryRequest;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.AddEntryResponse;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoriesResponseWrapper;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

public class StoryClient extends BaseClient implements StoryController{
	private static final String CREATE_STORY_ENDPOINT = "/";
	private static final String GET_PRIVATE_STORIES_ENDPOINT = "/privates";
	private static final String ADD_ENTRY_ENDPOINT = "/entry";
	private static final String GET_PRIVATE_STORY_ENDPOINT = "/private";
	private static final String LIKE_ENDPOINT = "/private/like";

	public StoryClient(){
		
	}
	
	@Override
	public ResponseEntity<CreateStoryResponse> createStory(String coToken, CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
		try{
			return restTemplate.exchange(urlStoryResolver(CREATE_STORY_ENDPOINT), HttpMethod.POST, httpEntity(createStoryRequest, coToken), CreateStoryResponse.class);
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
	public ResponseEntity<PrivateStoriesResponseWrapper> getPrivateStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException, StoryNotFoundException{
		try{
			return restTemplate.exchange(urlStoryResolver(GET_PRIVATE_STORIES_ENDPOINT), HttpMethod.GET, httpEntity(null, coToken), PrivateStoriesResponseWrapper.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			}else if(emw.getClazz() == StoryNotFoundException.class){
				throw new StoryNotFoundException();
			}else{
				throw new SomethingWentWrongException();
			}
		}
	}

	@Override
	public ResponseEntity<AddEntryResponse> addEntry(String coToken, AddEntryRequest entry) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException, StoryNotFoundException, AddEntryVersionException {
		try{
			return restTemplate.exchange(urlStoryResolver(ADD_ENTRY_ENDPOINT), HttpMethod.POST, httpEntity(entry, coToken), AddEntryResponse.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			} else if(emw.getClazz() == BadRequestException.class){
				throw new BadRequestException(emw.getBaseException());
			} else if(emw.getClazz() == AddEntryException.class){
				throw new AddEntryException();
			} else if(emw.getClazz() == StoryNotFoundException.class){
				throw new StoryNotFoundException();
			} else if(emw.getClazz() == AddEntryVersionException.class){
				throw new AddEntryVersionException();
			} else{
				throw new SomethingWentWrongException();
			}
		}
	}

	@Override
	public ResponseEntity<PrivateStoryResponse> getStoryForEdit(String coToken, String storyId) throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, UnauthorizedException{
		try{
			return restTemplate.exchange(urlStoryResolver("/" + storyId + GET_PRIVATE_STORY_ENDPOINT), HttpMethod.GET, httpEntity(null, coToken), PrivateStoryResponse.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			} else if(emw.getClazz() == UnauthorizedException.class){
				throw new UnauthorizedException();
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
	public void like(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, AlreadyLikedException, StoryNotFoundException, SomethingWentWrongException, UserLikingOwnStoryException{
		try{
			restTemplate.exchange(urlStoryResolver("/" + storyId + LIKE_ENDPOINT), HttpMethod.POST, httpEntity(null, coToken), String.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			} else if(emw.getClazz() == BadRequestException.class){
				throw new BadRequestException(emw.getBaseException());
			} else if(emw.getClazz() == UserLikingOwnStoryException.class){
				throw new UserLikingOwnStoryException();
			} else if(emw.getClazz() == AlreadyLikedException.class){
				throw new AlreadyLikedException();
			} else if(emw.getClazz() == StoryNotFoundException.class){
				throw new StoryNotFoundException();
			} else{
				throw new SomethingWentWrongException();
			}			
		}
	}
}
