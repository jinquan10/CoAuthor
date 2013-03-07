package com.nwm.coauthor.service.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnauthorizedException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoriesResponseWrapper;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

public class LikeTest extends TestSetup{
	@Test(expected = UserLikingOwnStoryException.class)
	public void like_WhenUserBelongsToStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, AlreadyLikedException, StoryNotFoundException, UserLikingOwnStoryException{
		List<UserModel> users = createUsers(3);
		
		ResponseEntity<CreateStoryResponse> createdStory = storyClient.createStory(users.get(0).getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		
		storyClient.like(users.get(0).getCoToken(), createdStory.getBody().getStoryId());
	}
	
	@Test
	public void userWith_NoPrivateStory_LikeAStory_AssertLikesIncremented_AssertThatUserHas0PrivateStories_AssertPrivateStoryException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException, UnauthorizedException, UserLikingOwnStoryException{
		List<UserModel> users = createUsers(3);
		
		UserModel user1 = users.get(0);
		UserModel user2 = users.get(1);
		UserModel userWithoutPrivateStory = users.get(2);
		
		List<String> fbFriends = new ArrayList<String>();
		
		fbFriends.add(user2.getFbId());
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user1.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, fbFriends)); 
		Assert.assertEquals(201, story.getStatusCode().value());
		
		storyClient.like(userWithoutPrivateStory.getCoToken(), story.getBody().getStoryId());
		
		ResponseEntity<PrivateStoryResponse> privateStoryResponse = storyClient.getStoryForEdit(user1.getCoToken(), story.getBody().getStoryId());
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
			storyClient.getStoryForEdit(userWithoutPrivateStory.getCoToken(), story.getBody().getStoryId());
		} catch(UnauthorizedException e){
			thrownStoryNotFoundException = true;
		}
		
		if(!thrownStoryNotFoundException){
			Assert.fail("userWithoutPrivateStory is not supposed to have any private stories.");
		}
	}
	
	@Test
	public void userWith_PrivateStory_LikeAStory_AssertLikesIncremented() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException, UnauthorizedException, UserLikingOwnStoryException{
		List<UserModel> users = createUsers(3);
		
		UserModel leader = users.get(0);
		UserModel member = users.get(1);
		UserModel nonMember = users.get(2);
		
		List<String> fbFriends = new ArrayList<String>();
		fbFriends.add(member.getFbId());
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(leader.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, fbFriends));
		
		storyClient.like(nonMember.getCoToken(), story.getBody().getStoryId());
		
		ResponseEntity<PrivateStoriesResponseWrapper> stories = storyClient.getPrivateStories(leader.getCoToken());
		PrivateStoryResponse storiesResponse = stories.getBody().getStories().get(0);
		Assert.assertEquals(new Integer(1), storiesResponse.getLikes());
		
		ResponseEntity<PrivateStoryResponse> oneStory = storyClient.getStoryForEdit(leader.getCoToken(), story.getBody().getStoryId());
		Assert.assertEquals(new Integer(1), oneStory.getBody().getLikes());
	}
	
	@Test(expected = StoryNotFoundException.class)
	public void userWith_PrivateStory_LikeANonExistantStory_AssertStoryNotFoundException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException, UserLikingOwnStoryException{
		List<UserModel> users = createUsers(1);
		
		UserModel user = users.get(0);
		
		storyClient.like(user.getCoToken(), new ObjectId().toString());
	}
	
	@Test(expected = AuthenticationUnauthorizedException.class)
	public void userWith_PrivateStory_LikeAStoryWithNullCOToken_AssertSomethingWentWrongException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException, UserLikingOwnStoryException{
		List<UserModel> users = createUsers(2);
		UserModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		storyClient.like(null, story.getBody().getStoryId());
	}
	
	@Test(expected = AuthenticationUnauthorizedException.class)
	public void userWith_PrivateStory_LikeAStoryWithEmptyCOToken_AssertSomethingWentWrongException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException, UserLikingOwnStoryException{
		List<UserModel> users = createUsers(2);
		UserModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		storyClient.like("", story.getBody().getStoryId());
	}
	
	@Test(expected = AuthenticationUnauthorizedException.class)
	public void userWith_PrivateStory_LikeAStoryWithNonExistantCOToken_AssertSomethingWentWrongException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException, UserLikingOwnStoryException{
		List<UserModel> users = createUsers(2);
		UserModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
		storyClient.like("nonExistantCOToken", story.getBody().getStoryId());
	}
	
	@Test(expected = AlreadyLikedException.class)
	public void user_LikesAStoryTwice_AssertAlreadyLikedException() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException, UserLikingOwnStoryException{
		List<UserModel> users = createUsers(3);
		
		UserModel leader = users.get(0);
		UserModel member = users.get(1);
		UserModel nonMember = users.get(2);
		
		List<String> fbFriends = new ArrayList<String>();
		fbFriends.add(member.getFbId());
		
		ResponseEntity<CreateStoryResponse> story = storyClient.createStory(leader.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, fbFriends));
		
		storyClient.like(nonMember.getCoToken(), story.getBody().getStoryId());
		storyClient.like(nonMember.getCoToken(), story.getBody().getStoryId());
	}	
}
