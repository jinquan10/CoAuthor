package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnauthorizedException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class PublishStoryTest extends BaseTest {
    @Test(expected = UserIsNotLeaderException.class)
    public void userIsNotLeader() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException, NonMemberException {
        UserModel leader = UserBuilder.createUser();
        UserModel member = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(member).build());
        try{
            storyClient.publishStory(member.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<StoryResponse> storyForEditResponse = storyClient.getMyStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
            assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
        }
    }

    @Test(expected = UserIsNotLeaderException.class)
    public void userIsANonMember() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException, NonMemberException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        NewStoryRequest storyRequest = NewStoryBuilder.init().title("The one").build();
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        try{
            storyClient.publishStory(nonMember.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<StoryResponse> storyForEditResponse = storyClient.getMyStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
            assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
        }
    }

    @Test(expected = NoTitleForPublishingException.class)
    public void hasNullTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException, NonMemberException {
        UserModel leader = UserBuilder.createUser();

        NewStoryRequest storyRequest = NewStoryBuilder.init().title(null).build();
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        try{
            storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<StoryResponse> storyForEditResponse = storyClient.getMyStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
            assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
        }
    }

    @Test(expected = StoryNotFoundException.class)
    public void nonExistantStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException {
        UserModel leader = UserBuilder.createUser();

        storyClient.publishStory(leader.getCoToken(), new ObjectId().toString());
    }    
    
    @Test
    public void publishesStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException, NonMemberException {
        UserModel leader = UserBuilder.createUser();

        NewStoryRequest storyRequest = NewStoryBuilder.init().title("The one").build();
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        
        ResponseEntity<StoryResponse> publishResponse = null;
        try{
            publishResponse = storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            assertEquals(true, publishResponse.getBody().getIsPublished());
            ResponseEntity<StoryResponse> storyForEditResponse = storyClient.getMyStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
            assertEquals(true, storyForEditResponse.getBody().getIsPublished());
        }
    }
    
    @Test(expected = NoTitleForPublishingException.class)
    public void hasEmptyTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException, NonMemberException {
        UserModel leader = UserBuilder.createUser();

        NewStoryRequest storyRequest = NewStoryBuilder.init().title("").build();
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        
        try{
            storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<StoryResponse> storyForEditResponse = storyClient.getMyStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
            assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
        }
    }    
}
