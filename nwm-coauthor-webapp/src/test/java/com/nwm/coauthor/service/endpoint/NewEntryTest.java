package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewEntryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class NewEntryTest extends BaseTest {
    @Test
    public void newEntry() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException, CannotGetEntriesException{
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse newStory = storyResponse.getBody();
        
        String lastEntry = "newEntry";
        ResponseEntity<StoryResponse> newEntryResponse = storyClient.newEntry(friend.getCoToken(), newStory.getStoryId(), NewEntryRequest.newEntry(lastEntry, newStory.getCurrEntryCharCount()));
        StoryResponse newEntryStory = newEntryResponse.getBody();
        
        assertEquals(HttpStatus.CREATED, newEntryResponse.getStatusCode());
        assertEquals((int)(newStory.getCurrEntryCharCount() + lastEntry.length()), (int)(newEntryStory.getCurrEntryCharCount()));
        assertNotEquals(newStory.getStoryLastUpdated(), newEntryStory.getStoryLastUpdated());
        assertEquals(newEntryStory.getLastFriendWithEntry(), friend.getFbId());
        assertEquals(newEntryStory.getLastEntry(), lastEntry);
        
        ResponseEntity<EntriesResponse> entriesResponse = storyClient.getEntries(leader.getCoToken(), newStory.getStoryId(), 0);
        EntriesResponse entries = entriesResponse.getBody();
        assertEquals(2, entries.getEntries().size());
        assertEquals(friend.getFbId(), entries.getEntries().get(1).getFbId());
        assertEquals(lastEntry, entries.getEntries().get(1).getEntry());
    }
    
    @Test
    public void multipleTripsToGetAllEntries(){
        
    }
    
    @Test(expected = StoryNotFoundException.class)
    public void storyNotFound() {
        
    }
    
    @Test(expected = NonMemberException.class)
    public void nonMember() {
        
    }    

    @Test(expected = VersioningException.class)
    public void invalidVersion() {
        
    }    

    @Test(expected = ConsecutiveEntryBySameMemberException.class)
    public void consecutiveEntryBySameMember() {
        
    }    
    
    @Test(expected = BadRequestException.class)
    public void nullVersion() {
        
    }    
    
    @Test(expected = BadRequestException.class)
    public void nullEntry() {
        
    }        

    @Test(expected = BadRequestException.class)
    public void emptyEntry() {
        
    }        
    
    @Test(expected = BadRequestException.class)
    public void lessThanMinCharEntry() {
        
    }        

    @Test(expected = BadRequestException.class)
    public void moreThanMaxCharEntry() {
        
    }      
}
