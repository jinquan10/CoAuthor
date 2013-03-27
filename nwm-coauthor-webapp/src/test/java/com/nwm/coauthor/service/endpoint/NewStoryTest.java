package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.response.StoryResponse;
import com.nwm.coauthor.service.util.StringUtil;

public class NewStoryTest extends BaseTest {
    @Test
    public void newStorySuccess() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        assertEquals(HttpStatus.CREATED, newStoryResponse.getStatusCode());
        
        StoryResponse responseBody = newStoryResponse.getBody();
        
        assertNotNull(responseBody);
        assertNotNull(responseBody.getStoryId());
        assertNotNull(responseBody.getLeaderFbId());
        assertNotNull(responseBody.getTitle());
        assertNotNull(responseBody.getNumCharacters());
        assertNotNull(responseBody.getIsPublished());
        assertFbFriends(responseBody);
        assertNotNull(responseBody.getLikes());
        assertNotNull(responseBody.getLastFriendWithEntry());
        assertNotNull(responseBody.getLastEntry());
        assertNotNull(responseBody.getStoryLastUpdated());
        assertNotNull(responseBody.getCurrEntryCount());
        
        assertEquals(leader.getFbId(), responseBody.getLeaderFbId());
        assertTrue(StringUtils.hasText(responseBody.getTitle()));
        assertFalse(responseBody.getIsPublished());
        assertEquals(2, responseBody.getFbFriends().size());
        assertEquals(new Long(0), responseBody.getLikes());
        assertTrue(StringUtils.hasText(responseBody.getLastFriendWithEntry()));
        assertTrue(StringUtils.hasText(responseBody.getLastEntry()));
    }

    private void assertFbFriends(StoryResponse responseBody){
        List<String> fbFriends = responseBody.getFbFriends();
        assertNotNull(fbFriends);
        
        for(String fbFriend : fbFriends){
            assertNotNull(fbFriend);
        }        
    }
    
    @Test(expected = AuthenticationUnauthorizedException.class)
    public void nullCoAuthorToken() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        storyClient.createStory(null, NewStoryBuilder.init().build());
    }

    @Test(expected = AuthenticationUnauthorizedException.class)
    public void emptyCoAuthorToken() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        storyClient.createStory("", NewStoryBuilder.init().build());
    }

    @Test
    public void bareRequestBody() throws SomethingWentWrongException, AuthenticationUnauthorizedException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().buildBare());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("entry"));
            Assert.assertTrue(batchErrors.containsKey("fbFriends"));
            Assert.assertTrue(batchErrors.containsKey("numCharacters"));
        }
    }

    @Test(expected = SomethingWentWrongException.class)
    public void nullRequestBody() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
        storyClient.createStory(leader.getCoToken(), null);
    }

    @Test
    public void nullEntry() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().entry(null).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("entry"));
        }
    }    

    @Test
    public void emptyEntry() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().entry("").build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("entry"));
        }
    }    

    @Test
    public void tooLittleEntry() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().entry("12").build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("entry"));
        }
    }        
    
    @Test
    public void entryTooBig() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().entry(StringUtil.repeat('a', 10001)).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("entry"));
        }
    }        
    
    @Test
    public void nullFbFriends() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbIdList(null).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("fbFriends"));
        }
    }    

    @Test
    public void emptyFbFriends() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbIdList(new ArrayList<String>()).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("fbFriends"));
        }
    }    
    
    @Test
    public void titleTooLong() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().title(StringUtil.repeat('a', 1001)).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("title"));
        }
    }    
}
