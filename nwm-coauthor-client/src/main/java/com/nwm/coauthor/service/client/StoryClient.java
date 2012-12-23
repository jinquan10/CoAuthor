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

public class StoryClient extends BaseClient implements StoryController{
	private static final String STORY_ENDPOINT = "/story";

	@Override
	public ResponseEntity<String> createStory(String coToken, CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException {
		ResponseEntity<String> response = null;
		
		try{
			response = restTemplate.exchange(urlResolver(STORY_ENDPOINT), HttpMethod.POST, httpEntity(createStoryRequest, coToken), String.class);
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
}
