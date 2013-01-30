package com.nwm.coauthor.service.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.model.LoginModel;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoriesResponseWrapper;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

public class LikeTest extends TestSetup{
	@Test
	public void userWith_NoPrivateStory_LikeAStory_AssertLikesIncremented_AssertThatUserHas0PrivateStories_AssertPrivateStoryException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException{
		List<LoginModel> users = createUsers(3);
		
		LoginModel user1 = users.get(0);
		LoginModel user2 = users.get(1);
		LoginModel userWithoutPrivateStory = users.get(2);
		
		List<String> fbFriends = new ArrayList<String>();
		
		fbFriends.add(user2.getFbId());
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user1.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, fbFriends)); 
		Assert.assertEquals(story.getStatusCode(), 201);
		
		storyClient.like(userWithoutPrivateStory.getCoToken(), story.getBody().getStoryId());
		
		ResponseEntity<PrivateStoryResponse> privateStoryResponse = storyClient.getPrivateStory(user1.getCoToken(), story.getBody().getStoryId());
		Assert.assertEquals(new Integer(1), privateStoryResponse.getBody().getLikes());
		
		boolean thrownStoryNotFoundException = false;
		try{
			ResponseEntity<PrivateStoriesResponseWrapper> privateStoryForUserWithoutPrivateStory = storyClient.getPrivateStories(userWithoutPrivateStory.getCoToken());
		} catch(StoryNotFoundException e){
			thrownStoryNotFoundException = true;
		}
		
		if(!thrownStoryNotFoundException){
			Assert.fail("userWithoutPriavte story is not supposed to have a list of private stories.");
		}

		try{
			storyClient.getPrivateStory(userWithoutPrivateStory.getCoToken(), story.getBody().getStoryId());
		} catch(StoryNotFoundException e){
			thrownStoryNotFoundException = true;
		}
		
		if(!thrownStoryNotFoundException){
			Assert.fail("userWithoutPrivateStory is not supposed to have any private stories.");
		}
	}
	
	@Test
	public void userWith_PrivateStory_LikeAStory_AssertLikesIncremented() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException{
		List<LoginModel> users = createUsers(2);
		
		LoginModel user = users.get(0);
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		
		storyClient.like(user.getCoToken(), story.getBody().getStoryId());
		
		ResponseEntity<PrivateStoriesResponseWrapper> stories = storyClient.getPrivateStories(user.getCoToken());
		PrivateStoryResponse storiesResponse = stories.getBody().getStories().get(0);
		Assert.assertEquals(new Integer(1), storiesResponse.getLikes());
		
		ResponseEntity<PrivateStoryResponse> oneStory = storyClient.getPrivateStory(user.getCoToken(), story.getBody().getStoryId());
		Assert.assertEquals(new Integer(1), oneStory.getBody().getLikes());
	}
	
	@Test(expected = StoryNotFoundException.class)
	public void userWith_PrivateStory_LikeANonExistantStory_AssertStoryNotFoundException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException{
		List<LoginModel> users = createUsers(1);
		
		LoginModel user = users.get(0);
		
		storyClient.like(user.getCoToken(), "nonStoryId");
	}
	
	@Test(expected = SomethingWentWrongException.class)
	public void userWith_PrivateStory_LikeWithNoStoryId_AssertSomethingWentWrongException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException{
		List<LoginModel> users = createUsers(1);
		
		LoginModel user = users.get(0);
		
		storyClient.like(user.getCoToken(), "");
	}
	
	@Test(expected = AuthenticationUnauthorizedException.class)
	public void userWith_PrivateStory_LikeAStoryWithNullCOToken_AssertSomethingWentWrongException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException{
		List<LoginModel> users = createUsers(2);
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		storyClient.like(null, story.getBody().getStoryId());
	}
	
	@Test(expected = AuthenticationUnauthorizedException.class)
	public void userWith_PrivateStory_LikeAStoryWithEmptyCOToken_AssertSomethingWentWrongException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException{
		List<LoginModel> users = createUsers(2);
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		storyClient.like("", story.getBody().getStoryId());
	}
	
	@Test(expected = AuthenticationUnauthorizedException.class)
	public void userWith_PrivateStory_LikeAStoryWithNonExistantCOToken_AssertSomethingWentWrongException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException{
		List<LoginModel> users = createUsers(2);
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		storyClient.like("nonExistantCOToken", story.getBody().getStoryId());
	}
	
	@Test(expected = AlreadyLikedException.class)
	public void user_LikesAStoryTwice_AssertAlreadyLikedException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException{
		List<LoginModel> users = createUsers(2);
		
		LoginModel user = users.get(0);
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		
		storyClient.like(user.getCoToken(), story.getBody().getStoryId());
		storyClient.like(user.getCoToken(), story.getBody().getStoryId());
	}	
}
