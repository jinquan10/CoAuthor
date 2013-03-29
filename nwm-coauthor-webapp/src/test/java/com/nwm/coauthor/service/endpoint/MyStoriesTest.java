package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class MyStoriesTest extends BaseTest {
    @Test
    public void hasNoStory() throws AuthenticationUnauthorizedException, SomethingWentWrongException{
        UserModel nonMember = UserBuilder.createUser();

        ResponseEntity<StoriesResponse> myStoriesResponse = storyClient.getMyStories(nonMember.getCoToken());
        assertNotNull(myStoriesResponse.getBody().getMyStories());
        assertEquals(0, myStoriesResponse.getBody().getMyStories().size());
    }

    @Test
    public void getMyStories() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
    	UserModel leader = UserBuilder.createUser();
    	
    	storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().title("asdf").build());
    	
    	ResponseEntity<StoriesResponse> myStoriesResponse = storyClient.getMyStories(leader.getCoToken());
    	assertNotNull(myStoriesResponse.getBody().getMyStories());
    	
    	StoryResponse responseBody = myStoriesResponse.getBody().getMyStories().get(0);
    	
        assertNotNull(responseBody);
        assertNotNull(responseBody.getStoryId());
        assertNotNull(responseBody.getLeaderFbId());
        assertNotNull(responseBody.getTitle());
        assertNotNull(responseBody.getIsPublished());
        assertFbFriends(responseBody);
        assertNotNull(responseBody.getLikes());
        assertNotNull(responseBody.getLastFriendWithEntry());
        assertNotNull(responseBody.getLastEntry());
        assertNotNull(responseBody.getStoryLastUpdated());
        assertNotNull(responseBody.getCurrEntryCharCount());
    	
    	assertNotNull(responseBody.getStoryId());
    	assertEquals(leader.getFbId(), responseBody.getLeaderFbId());
    	assertTrue(StringUtils.hasText(responseBody.getTitle()));
    	assertFalse(responseBody.getIsPublished());
    	assertEquals(2, responseBody.getFbFriends().size());
    	assertEquals(new Long(0), responseBody.getLikes());
    	assertTrue(StringUtils.hasText(responseBody.getLastFriendWithEntry()));
    	assertTrue(StringUtils.hasText(responseBody.getLastEntry()));
    	assertNotNull(responseBody.getStoryLastUpdated());
    }
    
    private void assertFbFriends(StoryResponse responseBody){
        List<String> fbFriends = responseBody.getFbFriends();
        assertNotNull(fbFriends);
        
        for(String fbFriend : fbFriends){
            assertNotNull(fbFriend);
        }        
    }    
    
    @Test
    public void getMyStoriesAsLeader() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
    	UserModel leader = UserBuilder.createUser();
    	
    	storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
    	storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
    	
    	ResponseEntity<StoriesResponse> myStoriesResponse = storyClient.getMyStories(leader.getCoToken());
    	assertNotNull(myStoriesResponse.getBody().getMyStories());
    	assertEquals(2, myStoriesResponse.getBody().getMyStories().size());
    }

    @Test
    public void getMyStoriesAsMember() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException{
    	UserModel leader = UserBuilder.createUser();
    	UserModel member = UserBuilder.createUser();
    	
    	storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(member).build());
    	storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(member).build());
    	
    	ResponseEntity<StoriesResponse> myStoriesResponse = storyClient.getMyStories(member.getCoToken());
    	assertNotNull(myStoriesResponse.getBody().getMyStories());
    	assertEquals(2, myStoriesResponse.getBody().getMyStories().size());
    }
    
    @Test
    public void getMyStoriesAsLeaderAndMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException{
    	UserModel leader = UserBuilder.createUser();
    	UserModel member = UserBuilder.createUser();
    	
    	storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(member).build());
    	storyClient.createStory(member.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(leader).build());
    	
    	ResponseEntity<StoriesResponse> myStoriesResponse = storyClient.getMyStories(leader.getCoToken());
    	assertNotNull(myStoriesResponse.getBody().getMyStories());
    	assertEquals(2, myStoriesResponse.getBody().getMyStories().size());
    	
    	myStoriesResponse = storyClient.getMyStories(member.getCoToken());
    	assertNotNull(myStoriesResponse.getBody().getMyStories());
    	assertEquals(2, myStoriesResponse.getBody().getMyStories().size());    	
    }
    
//    @Test
//    public void getPrivateStories_UserShouldSeeOneEntry_WhenMoreThanOneEntryIsSubmitted() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException,
//            AddEntryException, InterruptedException, StoryNotFoundException, AddEntryVersionException {
//        List<UserModel> users = UserBuilder.createUsers(null);
//
//        UserModel user = users.get(0);
//
//        // - Create a story with user0, and add the rest of the users as user0's
//        // friends
//        ResponseEntity<NewStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
//
//        String storyId = response.getBody().getStoryId();
//
//        EntryRequest request = new EntryRequest();
//        request.setEntry("hahahaha");
//        request.setStoryId(storyId);
//        request.setVersion(0);
//
//        storyClient.addEntry(users.get(1).getCoToken(), request);
//
//        ResponseEntity<PrivateStoriesResponseWrapper> privateStoriesResponse = storyClient.getPrivateStories(user.getCoToken());
//
//        PrivateStoriesResponseWrapper body = privateStoriesResponse.getBody();
//        Assert.assertNotNull(body);
//
//        List<PrivateStoryResponse> stories = body.getStories();
//        Assert.assertNotNull(stories);
//
//        Assert.assertTrue(stories.size() == 1);
//
//        for (int j = 0; j < users.size(); j++) {
//            PrivateStoryResponse story = stories.get(0);
//
//            Assert.assertTrue(StringUtils.hasText(story.get_id()));
//            Assert.assertTrue(StringUtils.hasText(story.getLastFriendEntry()));
//            Assert.assertFalse(story.getIsPublished());
//            Assert.assertNotNull(story.getEntries());
//            Assert.assertTrue(story.getEntries().size() == 1);
//            Assert.assertNotNull(story.getFbFriends());
//            Assert.assertNotNull(story.getNumCharacters());
//            Assert.assertTrue(StringUtils.hasText(story.getLeaderFbId()));
//            Assert.assertTrue(story.getVersion() == 1);
//        }
//    }
}
