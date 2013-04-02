package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertTrue;

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
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class NewFriendsTest extends BaseTest{
    @Test
    public void newFriends_ThenGetStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse story = createStoryResponse.getBody();
        
        String newFriendFbId = String.valueOf(Math.random());
        
        ResponseEntity<NewFriendsResponse> newFriendsResponse = storyClient.newFriends(leader.getCoToken(), story.getStoryId(), NewFriendsRequest.init().addNewFriend(newFriendFbId));
        NewFriendsResponse newFriends = newFriendsResponse.getBody();
        
        assertTrue(newFriends.getNewFriends().contains(newFriendFbId));
    }
}
