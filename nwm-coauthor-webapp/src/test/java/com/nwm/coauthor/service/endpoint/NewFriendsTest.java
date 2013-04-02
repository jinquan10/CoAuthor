package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyAMemberException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewFriendsRequest;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class NewFriendsTest extends BaseTest {
    @Test
    public void newFriend() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();

        String newFriendFbId = String.valueOf(Math.random());

        ResponseEntity<StoryResponse> newFriendsResponse = storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(newFriendFbId));
        StoryResponse newFriendsStory = newFriendsResponse.getBody();

        assertTrue(newFriendsStory.getFbFriends().contains(newFriendFbId));
    }

    @Test
    public void newFriends() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();

        String newFriendFbId = String.valueOf(Math.random());
        String newFriendFbId2 = String.valueOf(Math.random());

        ResponseEntity<StoryResponse> newFriendsResponse = storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(newFriendFbId).addNewFriend(newFriendFbId2));
        StoryResponse newFriendsStory = newFriendsResponse.getBody();

        assertTrue(newFriendsStory.getFbFriends().contains(newFriendFbId));
        assertTrue(newFriendsStory.getFbFriends().contains(newFriendFbId2));
    }
    
    @Test
    public void newFriends_ThenNewFriendGetsStories() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();

        String newFriendFbId = String.valueOf(Math.random());

        ResponseEntity<StoryResponse> newFriendsResponse = storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(newFriendFbId));
        StoryResponse newFriendsStory = newFriendsResponse.getBody();

        assertTrue(newFriendsStory.getFbFriends().contains(newFriendFbId));

        // - fake the login
        UserModel newFriend = UserBuilder.createUser(newFriendFbId);
        ResponseEntity<StoriesResponse> myStoriesResponse = storyClient.getMyStories(newFriend.getCoToken());
        StoriesResponse myStories = myStoriesResponse.getBody();

        List<StoryResponse> stories = myStories.getMyStories();
        assertEquals(1, stories.size());

        StoryResponse myStory = stories.get(0);
        assertEquals(story.getStoryId(), myStory.getStoryId());
        assertEquals(3, myStory.getFbFriends().size());
        assertNotEquals(story.getStoryLastUpdated(), myStory.getStoryLastUpdated());
    }

    @Test(expected = StoryNotFoundException.class)
    public void storyNotFound() throws SomethingWentWrongException, BadRequestException {
        UserModel leader = UserBuilder.createUser();

        String newFriendFbId = String.valueOf(Math.random());

        storyClient.newFriends(leader.getCoToken(), new ObjectId().toString(), NewFriendsRequest.init().addNewFriend(newFriendFbId));
    }

    @Test(expected = NonMemberException.class)
    public void nonMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();

        String newFriendFbId = String.valueOf(Math.random());
        storyClient.newFriends(nonMember.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(newFriendFbId));
    }
    
    @Test(expected = SomethingWentWrongException.class)
    public void nullRequest() throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException{
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();

        storyClient.newFriends(leader.getCoToken(), story.getStoryId(), null);        
    }
    
    @Test(expected = BadRequestException.class)
    public void zeroLengthRequest() throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException{
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();

        try{
            storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init());
        }catch(BadRequestException e){
            assertTrue(e.getBatchErrors().containsKey("newFriends"));
            throw e;
        }
    }

    @Test(expected = BadRequestException.class)
    public void nullListRequest() throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException{
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();

        NewFriendsRequest request = NewFriendsRequest.init();
        request.setNewFriends(null);
        
        try{
            storyClient.newFriends(leader.getCoToken(), story.getStoryId(), request);
        }catch(BadRequestException e){
            assertTrue(e.getBatchErrors().containsKey("newFriends"));
            throw e;
        }
    }
    
    @Test(expected = AlreadyAMemberException.class)
    public void alreadyAMemberALeader() throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException{
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();

        storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(leader.getFbId()));        
    }
    
    @Test(expected = AlreadyAMemberException.class)
    public void alreadyAMemberAMember() throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException{
        UserModel leader = UserBuilder.createUser();
        UserModel member = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(member).build());
        StoryResponse story = createStoryResponse.getBody();

        storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(member.getFbId()));
    }
    
    @Test(expected = AlreadyAMemberException.class)
    public void alreadyAMemberAMembers() throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException{
        UserModel leader = UserBuilder.createUser();
        UserModel member = UserBuilder.createUser();
        UserModel member2 = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(member).fbFriendsFromUserModel(member2).build());
        StoryResponse story = createStoryResponse.getBody();

        storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(member.getFbId()));
    }    
}
