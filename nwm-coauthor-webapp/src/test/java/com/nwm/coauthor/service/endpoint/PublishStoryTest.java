package com.nwm.coauthor.service.endpoint;


import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnauthorizedException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

public class PublishStoryTest extends BaseTest {
    @Test(expected = UserIsNotLeaderException.class)
    public void userIsNotLeader() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
        UserModel leader = UserBuilder.createUser();
        UserModel member = UserBuilder.createUser();

        CreateStoryRequest storyRequest = CreateStoryBuilder.init().title("The one").build();
        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        try{
            storyClient.publishStory(member.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
            Assert.assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
        }
    }

    @Test(expected = UserIsNotLeaderException.class)
    public void userIsANonMember() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        CreateStoryRequest storyRequest = CreateStoryBuilder.init().title("The one").build();
        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        try{
            storyClient.publishStory(nonMember.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
            Assert.assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
        }
    }

    @Test(expected = NoTitleForPublishingException.class)
    public void hasNullTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
        UserModel leader = UserBuilder.createUser();

        CreateStoryRequest storyRequest = CreateStoryBuilder.init().title(null).build();
        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        try{
            storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
            Assert.assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
        }
    }

    @Test(expected = StoryNotFoundException.class)
    public void nonExistantStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException {
        UserModel leader = UserBuilder.createUser();

        storyClient.publishStory(leader.getCoToken(), new ObjectId().toString());
    }    
    
    @Test
    public void publishesStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
        UserModel leader = UserBuilder.createUser();

        CreateStoryRequest storyRequest = CreateStoryBuilder.init().title("The one").build();
        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        
        try{
            storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
            Assert.assertEquals(true, storyForEditResponse.getBody().getIsPublished());            
        }
    }
    
    @Test(expected = NoTitleForPublishingException.class)
    public void hasEmptyTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
        UserModel leader = UserBuilder.createUser();

        CreateStoryRequest storyRequest = CreateStoryBuilder.init().title("").build();
        ResponseEntity<CreateStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
        
        try{
            storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        }finally{
            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
            Assert.assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
        }
    }    
}
