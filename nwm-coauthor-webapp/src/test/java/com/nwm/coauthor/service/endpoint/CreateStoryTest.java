package com.nwm.coauthor.service.endpoint;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.CreateStoryBadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;

public class CreateStoryTest extends TestSetup{
	@Test
	public void createStorySuccessTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException{
		ResponseEntity<String> response = storyClient.createStory(coToken, CreateStoryBuilder.createValidStory());
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void createStoryBadRequestListTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException{
		try{
			storyClient.createStory(coToken, new CreateStoryRequest());
		}catch(CreateStoryBadRequestException e){
			Map<String, String> batchErrors = e.getBatchErrors();
			
			Assert.assertTrue(batchErrors.size() == 3);
			Assert.assertTrue(e.getStatusCode() == HttpStatus.BAD_REQUEST.value());
		}
	}
	
	@Test
	public void createStoryNullResourceTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException{
		try{
			storyClient.createStory(coToken, null);
		}catch(CreateStoryBadRequestException e){
			Map<String, String> batchErrors = e.getBatchErrors();
			
			Assert.assertTrue(batchErrors.size() == 3);
			Assert.assertTrue(e.getStatusCode() == HttpStatus.BAD_REQUEST.value());
		}
	}
	
	@Test(expected = CreateStoryBadRequestException.class)
	public void createStoryLengthyTitleTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException{
		CreateStoryRequest request = CreateStoryBuilder.createValidStory();
		request.setTitle("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
		storyClient.createStory(coToken, request);
	}
}
