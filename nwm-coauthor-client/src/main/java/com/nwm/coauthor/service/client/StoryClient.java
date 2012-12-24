package com.nwm.coauthor.service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.CreateStoryBadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.mapping.ExceptionMapper;
import com.nwm.coauthor.service.controller.StoryController;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.GetPrivateStoriesResponseWrapper;

public class StoryClient extends BaseClient implements StoryController{
	private static final String CREATE_STORY_ENDPOINT = "/story";
	private static final String GET_PRIVATE_STORIES_ENDPOINT = "/story/private";

	@Override
	public ResponseEntity<String> createStory(String coToken, CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException {
		ResponseEntity<String> response = null;
		
		try{
			response = restTemplate.exchange(urlResolver(CREATE_STORY_ENDPOINT), HttpMethod.POST, httpEntity(createStoryRequest, coToken), String.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapper em = convertToExceptionMapper(e);
			
			if(em.getClazz() == SomethingWentWrongException.class){
				throw new SomethingWentWrongException();
			}else if(em.getClazz() == CreateStoryBadRequestException.class){
				throw new CreateStoryBadRequestException(em.getBaseException());
			}else{
				throw new AuthenticationUnauthorizedException();
			}
		}
		
		return response;
	}

	@Override
	public ResponseEntity<GetPrivateStoriesResponseWrapper> getPrivateStories(String coToken) throws AuthenticationUnauthorizedException {
		ResponseEntity<GetPrivateStoriesResponseWrapper> response = null;
		
		try{
			response = restTemplate.exchange(urlResolver(GET_PRIVATE_STORIES_ENDPOINT), HttpMethod.GET, httpEntity(null, coToken), GetPrivateStoriesResponseWrapper.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapper em = convertToExceptionMapper(e);
			
			if(em.getClazz() == AuthenticationUnauthorizedException.class){
				throw new AuthenticationUnauthorizedException();
			}
		}
		
		return response;
	}
}
