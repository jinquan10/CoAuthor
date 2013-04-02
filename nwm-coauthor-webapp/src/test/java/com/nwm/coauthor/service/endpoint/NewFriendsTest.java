package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewFriendsRequest;
import com.nwm.coauthor.service.resource.response.NewFriendsResponse;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class NewFriendsTest extends BaseTest{
    @Test
    public void newFriends() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();
        
        String newFriendFbId = String.valueOf(Math.random());
        
        ResponseEntity<StoryResponse> newFriendsResponse = storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(newFriendFbId));
        StoryResponse newFriendsStory = newFriendsResponse.getBody();
        
        assertTrue(newFriendsStory.getFbFriends().contains(newFriendFbId));
    }
    
    @Test
    public void newFriends_ThenNewFriendGetsStories() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
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
}
