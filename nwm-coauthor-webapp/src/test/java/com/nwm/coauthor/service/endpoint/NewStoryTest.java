package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.response.NewStoryResponse;
import com.nwm.coauthor.service.util.StringUtil;

public class NewStoryTest extends BaseTest {
    @Test
    public void newStorySuccess() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<NewStoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        assertEquals(HttpStatus.CREATED, newStoryResponse.getStatusCode());
        assertNotNull(newStoryResponse.getBody());
        assertNotNull(newStoryResponse.getBody().getStoryId());
        assertNotNull(newStoryResponse.getBody().getStoryLastUpdated());
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
    public void entryTooBig() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().entry(StringUtil.repeat('a', 100)).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("entry"));
        }
    }        
    
    @Test
    public void nullFbFriends() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriends(null).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("fbFriends"));
        }
    }    

    @Test
    public void emptyFbFriends() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriends(new ArrayList<String>()).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("fbFriends"));
        }
    }    
    
    @Test
    public void nullCharacters() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().numCharacters(null).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("numCharacters"));
        }
    }    

    @Test
    public void tooManyCharacters() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
    	
    	try {
            storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().numCharacters(10000).build());
        } catch (BadRequestException e) {
            Map<String, String> batchErrors = e.getBatchErrors();

            Assert.assertTrue(batchErrors.containsKey("numCharacters"));
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
