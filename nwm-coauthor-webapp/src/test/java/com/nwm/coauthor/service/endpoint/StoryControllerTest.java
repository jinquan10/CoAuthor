package com.nwm.coauthor.service.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.CreateStoryBadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.client.StoryClient;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;


public class StoryControllerTest extends TestSetup{
	private StoryClient client = new StoryClient();
	
	@Test
	public void createStorySuccessTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException{
		List<String> fbFriends = new ArrayList<String>();
		fbFriends.add("100000029500725");
		
		CreateStoryRequest createStoryRequest = new CreateStoryRequest();
		createStoryRequest.setEntry("12345");
		createStoryRequest.setFbFriends(fbFriends);
		createStoryRequest.setNumCharacters(5);
		createStoryRequest.setTitle(null);
		
		ResponseEntity<String> response = client.createStory(coToken, createStoryRequest);
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void createStoryBadRequestListTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException{
		CreateStoryRequest createStoryRequest = new CreateStoryRequest();
		ResponseEntity<String> response = null;
		
		try{
			response = client.createStory(coToken, createStoryRequest);
		}catch(CreateStoryBadRequestException e){
			Map<String, String> batchErrors = e.getBatchErrors();
			
			Assert.assertTrue(batchErrors.size() == 3);
		}
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void createStoryNullResourceTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException{
		ResponseEntity<String> response = client.createStory(coToken, null);
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
}
