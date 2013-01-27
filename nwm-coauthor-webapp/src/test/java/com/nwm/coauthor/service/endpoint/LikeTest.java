package com.nwm.coauthor.service.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.model.LoginModel;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoriesResponseWrapper;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

// - 2. As a user that has a private story, like a Story.
// - 3. Like a nonexistant story
// - 4. no storyId like request
// - 5. null coauthorToken
// - 6. emptyCoAuthorToken
// - 7. nonexistant coauthorToken
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
			
		}
		
		Assert.fail("userWithoutPrivateStory is not supposed to have any private stories.");
	}
}
