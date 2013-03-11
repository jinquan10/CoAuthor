package com.nwm.coauthor.service.endpoint;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyPublishedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.ChangeTitleRequest;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;

public class ChangeTitleTest extends BaseTest {
    @Test(expected = StoryNotFoundException.class)
    public void nonExistantStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel user = UserBuilder.createUser();

        storyClient.changeStoryTitle(user.getCoToken(), new ObjectId().toString(), ChangeTitleRequest.initWithTitle("title"));
    } 

    @Test(expected = BadRequestException.class)
    public void nullTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel user = UserBuilder.createUser();

        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.init().build());

        try {
            storyClient.changeStoryTitle(user.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle(null));
        } catch (BadRequestException e) {
            if (!e.getBatchErrors().containsKey("title")) {
                Assert.fail("Should of had title errorCode.");
            }

            throw new BadRequestException(e);
        }
    }

    @Test(expected = BadRequestException.class)
    public void emptyTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel user = UserBuilder.createUser();

        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.init().build());

        try {
            storyClient.changeStoryTitle(user.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle(""));
        } catch (BadRequestException e) {
            if (!e.getBatchErrors().containsKey("title")) {
                Assert.fail("Should of had title errorCode.");
            }

            throw new BadRequestException(e);
        }
    }

    @Test(expected = UserIsNotLeaderException.class)
    public void justAMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel leader = UserBuilder.createUser();
        List<UserModel> friends = UserBuilder.createUsers(2);
        
        CreateStoryRequest request = CreateStoryBuilder.init().fbFriends(UserBuilder.exchangeForFbIds(friends)).build();
        
        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), request);
        storyClient.changeStoryTitle(friends.get(0).getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
    }

    @Test(expected = UserIsNotLeaderException.class)
    public void notAMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();
        
        CreateStoryRequest request = CreateStoryBuilder.init().build();
        
        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), request);
        storyClient.changeStoryTitle(nonMember.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
    }    
    
    @Test(expected = AlreadyPublishedException.class)
    public void alreadyPublished() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, AlreadyPublishedException{
        UserModel leader = UserBuilder.createUser();
        CreateStoryRequest storyRequest = CreateStoryBuilder.init().title("The one").build();

        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        
        storyClient.changeStoryTitle(leader.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
    }

    @Test
    public void changeTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel leader = UserBuilder.createUser();
        CreateStoryRequest storyRequest = CreateStoryBuilder.init().title("The one").build();

        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        storyClient.changeStoryTitle(leader.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
    }
}
