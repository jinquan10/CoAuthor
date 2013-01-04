package com.nwm.coauthor.service.endpoint;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;

public class CreateStoryTest extends TestSetup{
	@Test
	public void createStorySuccessTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
		for(int i = 0; i < users.size(); i++){
			ResponseEntity<String> response = storyClient.createStory(users.get(i).getCoToken(), CreateStoryBuilder.createValidStory(users, i));
			
			Assert.assertTrue(response.getStatusCode() == HttpStatus.CREATED);
			Assert.assertNotNull(response.getBody());
			Assert.assertTrue(StringUtils.hasText(response.getBody()));
		}
	}
	
	@Test(expected = SomethingWentWrongException.class)
	public void createStoryNullCoTokenTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
		storyClient.createStory(null, CreateStoryBuilder.createValidStory(users, 0));
	}	
	
	@Test
	public void createStoryBadRequestListTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException{
		for(int i = 0; i < users.size(); i++){
			try {
				storyClient.createStory(users.get(i).getCoToken(), new CreateStoryRequest());
			} catch (BadRequestException e) {
				Map<String, String> batchErrors = e.getBatchErrors();

				Assert.assertTrue(batchErrors.size() == 3);
				Assert.assertTrue(e.getStatusCode() == HttpStatus.BAD_REQUEST.value());
			}
		}
	}
	
	@Test
	public void createStoryNullResourceTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
		for(int i = 0; i < users.size(); i++){
			try {
				storyClient.createStory(users.get(i).getCoToken(), null);
			} catch (BadRequestException e) {
				Map<String, String> batchErrors = e.getBatchErrors();

				Assert.assertTrue(e.toString(), batchErrors.size() == 3);
				Assert.assertTrue(e.getStatusCode() == HttpStatus.BAD_REQUEST.value());
			}
		}
	}
	
	@Test(expected = BadRequestException.class)
	public void createStoryLengthyTitleTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
		for(int i = 0; i < users.size(); i++){
			CreateStoryRequest request = CreateStoryBuilder.createValidStory(users, i);
			request.setTitle("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
			storyClient.createStory(users.get(i).getCoToken(), request);
		}
	}
}
