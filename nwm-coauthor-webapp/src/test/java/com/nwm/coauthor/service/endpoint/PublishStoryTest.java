package com.nwm.coauthor.service.endpoint;

import java.util.List;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;

public class PublishStoryTest extends TestSetup{
	@Test(expected = UserIsNotLeaderException.class)
	public void userIsNotLeader() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException{
		List<UserModel> friends = UserBuilder.createUsers(2);
		UserModel leader = UserBuilder.createUser();
		UserModel member = friends.get(0);
		
		CreateStoryRequest storyRequest = CreateStoryBuilder.createStoryWithTitle(leader, friends, "The one");
		ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
		storyClient.publishStory(member.getCoToken(), storyResponse.getBody().getStoryId());
	}
	
	@Test(expected = UserIsNotLeaderException.class)
	public void userIsANonMember() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
		List<UserModel> friends = UserBuilder.createUsers(2);
		UserModel leader = UserBuilder.createUser();
		UserModel nonMember = UserBuilder.createUser();
		
		CreateStoryRequest storyRequest = CreateStoryBuilder.createStoryWithTitle(leader, friends, "The one");
		ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
		storyClient.publishStory(nonMember.getCoToken(), storyResponse.getBody().getStoryId());
	}
	
	@Test(expected = NoTitleForPublishingException.class)
	public void hasNoTitle(){
		
	}
	
	@Test
	public void publishesStory(){
		
	}
}
