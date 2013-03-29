package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.MoreEntriesLeftException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewEntryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.EntryResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;
import com.nwm.coauthor.service.util.StringUtil;

public class NewEntryTest extends BaseTest {
    @Test
    public void newEntry() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException, CannotGetEntriesException, MoreEntriesLeftException{
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
        
        ResponseEntity<EntriesResponse> entriesResponse = storyClient.getEntries(leader.getCoToken(), newStory.getStoryId(), 0, newEntryStory.getCurrEntryCharCount());
        EntriesResponse entries = entriesResponse.getBody();
        assertEquals(2, entries.getEntries().size());
        assertEquals(friend.getFbId(), entries.getEntries().get(1).getFbId());
        assertEquals(lastEntry, entries.getEntries().get(1).getEntry());
    }
    
    @Test
    public void multipleTripsToGetAllEntries() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException, CannotGetEntriesException{
        UserModel leader = UserBuilder.createUser();
        
        int numCharsPerEntry = 1000;
        int numEntries = 100;
        int totalEntries = numEntries + 1;
        
        StoryResponse story = insertATonOfEntries(leader, numCharsPerEntry, numEntries, totalEntries);
        
        List<EntryResponse> entries = getATonOfEntries(leader, story);
        
        assertEquals(totalEntries, entries.size());
    }

    private List<EntryResponse> getATonOfEntries(UserModel leader, StoryResponse story) throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException,
            StoryNotFoundException, VersioningException {
        
        ResponseEntity<EntriesResponse> entriesResponse = null;
        try {
            entriesResponse = storyClient.getEntries(leader.getCoToken(), story.getStoryId(), 0, story.getCurrEntryCharCount());
        } catch (MoreEntriesLeftException e) {
            List<EntryResponse> entries = e.getEntriesResponse().getEntries();
            entries.addAll(getATonOfEntries(leader, story));
            
            return entries;
        }
        
        return entriesResponse.getBody().getEntries();
    }

    @Test(expected = MoreEntriesLeftException.class)
    public void moreEntriesToFetch() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException, CannotGetEntriesException, MoreEntriesLeftException{
        UserModel leader = UserBuilder.createUser();

        int numCharsPerEntry = 1000;
        int numEntries = 100;
        int totalEntries = numEntries + 1;
        
        StoryResponse story = insertATonOfEntries(leader, numCharsPerEntry, numEntries, totalEntries);
        
        storyClient.getEntries(leader.getCoToken(), story.getStoryId(), 0, story.getCurrEntryCharCount());
    }
    
    private StoryResponse insertATonOfEntries(UserModel leader, int numCharsPerEntry, int numEntries, int totalEntries) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException{
        UserModel friend = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse newStory = storyResponse.getBody();
        
        String entry = StringUtil.repeat('a', numCharsPerEntry);
        
        boolean isLeaderTurn = false;
        StoryResponse newEntryResponseStory = newStory;             
        for(int i = 0; i < numEntries; i++){
            if(isLeaderTurn){
                ResponseEntity<StoryResponse> newEntryResponse = storyClient.newEntry(leader.getCoToken(), newStory.getStoryId(), NewEntryRequest.newEntry(entry, newEntryResponseStory.getCurrEntryCharCount()));
                newEntryResponseStory = newEntryResponse.getBody();
            }else{
                ResponseEntity<StoryResponse> newEntryResponse = storyClient.newEntry(friend.getCoToken(), newStory.getStoryId(), NewEntryRequest.newEntry(entry, newEntryResponseStory.getCurrEntryCharCount()));
                newEntryResponseStory = newEntryResponse.getBody();
            }
            
            isLeaderTurn = !isLeaderTurn;
        }
        
        return newEntryResponseStory;
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
