package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyPublishedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.ChangeTitleRequest;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.StoryResponse;


public class ChangeTitleTest extends BaseTest {
    @Test(expected = StoryNotFoundException.class)
    public void nonExistantStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel user = UserBuilder.createUser();

        storyClient.changeTitle(user.getCoToken(), new ObjectId().toString(), ChangeTitleRequest.initWithTitle("title"));
    } 

    @Test(expected = BadRequestException.class)
    public void nullTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel user = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(user.getCoToken(), NewStoryBuilder.init().build());

        try {
            storyClient.changeTitle(user.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle(null));
        } catch (BadRequestException e) {
            if (!e.getBatchErrors().containsKey("title")) {
                fail("Should of had title errorCode.");
            }

            throw new BadRequestException(e);
        }
    }

    @Test(expected = BadRequestException.class)
    public void emptyTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel user = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(user.getCoToken(), NewStoryBuilder.init().build());

        try {
            storyClient.changeTitle(user.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle(""));
        } catch (BadRequestException e) {
            if (!e.getBatchErrors().containsKey("title")) {
                fail("Should of had title errorCode.");
            }

            throw new BadRequestException(e);
        }
    }

    @Test(expected = UserIsNotLeaderException.class)
    public void justAMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel leader = UserBuilder.createUser();
        UserModel member = UserBuilder.createUser();
        
        NewStoryRequest request = NewStoryBuilder.init().fbFriendsFromUserModel(member).build();
        
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), request);
        storyClient.changeTitle(member.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
    }

    @Test(expected = UserIsNotLeaderException.class)
    public void notAMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();
        
        NewStoryRequest request = NewStoryBuilder.init().build();
        
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), request);
        storyClient.changeTitle(nonMember.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
    }    
    
    @Test(expected = AlreadyPublishedException.class)
    public void alreadyPublished() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, AlreadyPublishedException{
        UserModel leader = UserBuilder.createUser();
        NewStoryRequest storyRequest = NewStoryBuilder.init().title("The one").build();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        
        storyClient.changeTitle(leader.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
    }

    @Test
    public void changeTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
        UserModel leader = UserBuilder.createUser();
        NewStoryRequest storyRequest = NewStoryBuilder.init().title("The one").build();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        storyClient.changeTitle(leader.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
    }
    
    @Test
    public void andPublish() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException, NoTitleForPublishingException{
        UserModel leader = UserBuilder.createUser();
        NewStoryRequest storyRequest = NewStoryBuilder.init().build();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        ResponseEntity<StoryResponse> changedTitleResponse = storyClient.changeTitle(leader.getCoToken(), storyResponse.getBody().getStoryId(), ChangeTitleRequest.initWithTitle("title"));
        StoryResponse story = changedTitleResponse.getBody();
        
        assertEquals(story.getTitle(), "title");
        
        ResponseEntity<StoryResponse> publishResponse = storyClient.publishStory(leader.getCoToken(), story.getStoryId());
        story = publishResponse.getBody();
        
        assertTrue(story.getIsPublished());
    }
}
