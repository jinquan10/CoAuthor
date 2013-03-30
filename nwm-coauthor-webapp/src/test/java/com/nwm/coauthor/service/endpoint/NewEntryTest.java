package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.*;

import java.util.List;

import org.bson.types.ObjectId;
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
    public void newEntry() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException, NonMemberException,
            ConsecutiveEntryBySameMemberException, CannotGetEntriesException, MoreEntriesLeftException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse newStory = storyResponse.getBody();

        String lastEntry = "newEntry";
        ResponseEntity<StoryResponse> newEntryResponse = storyClient.newEntry(friend.getCoToken(), newStory.getStoryId(), NewEntryRequest.newEntry(lastEntry, newStory.getCurrEntryCharCount()));
        StoryResponse newEntryStory = newEntryResponse.getBody();

        assertEquals(HttpStatus.CREATED, newEntryResponse.getStatusCode());
        assertEquals((int) (newStory.getCurrEntryCharCount() + lastEntry.length()), (int) (newEntryStory.getCurrEntryCharCount()));
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
    public void multipleTripsToGetAllEntries() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException,
            NonMemberException, ConsecutiveEntryBySameMemberException, CannotGetEntriesException {
        UserModel leader = UserBuilder.createUser();

        int numCharsPerEntry = 794;
        int numEntries = 124;
        int totalEntries = numEntries + 1;

        StoryResponse story = insertATonOfEntries(leader, numCharsPerEntry, numEntries, totalEntries);

        List<EntryResponse> entries = getATonOfEntries(leader, story, 0);

        Integer calculatedChar = calculateNumCharsFromEntries(entries);

        assertEquals(calculatedChar, story.getCurrEntryCharCount());
        assertEquals(totalEntries, entries.size());
    }

    @Test
    public void newEntriesReturnedInTheRightOrder() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException,
            NonMemberException, ConsecutiveEntryBySameMemberException, CannotGetEntriesException {
        UserModel leader = UserBuilder.createUser();

        int numCharsPerEntry = 194;
        int numEntries = 124;
        int totalEntries = numEntries + 1;

        StoryResponse story = insertATonOfEntries(leader, numCharsPerEntry, numEntries, totalEntries);

        List<EntryResponse> entries = getATonOfEntries(leader, story, 0);

        assertInTheRightOrder(entries);
        Integer calculatedChar = calculateNumCharsFromEntries(entries);

        assertEquals(calculatedChar, story.getCurrEntryCharCount());
        assertEquals(totalEntries, entries.size());
    }    
    
    private void assertInTheRightOrder(List<EntryResponse> entries) {
        int charCount = 0;
        
        for(EntryResponse entry : entries){
            if(entry.getCurrCharCount() < charCount){
                fail();
            }else{
                charCount = entry.getCurrCharCount();
            }
        }
    }

    @Test(expected = MoreEntriesLeftException.class)
    public void moreEntriesToFetch() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException, NonMemberException,
            ConsecutiveEntryBySameMemberException, CannotGetEntriesException, MoreEntriesLeftException {
        UserModel leader = UserBuilder.createUser();

        int numCharsPerEntry = 1000;
        int numEntries = 143;
        int totalEntries = numEntries + 1;

        StoryResponse story = insertATonOfEntries(leader, numCharsPerEntry, numEntries, totalEntries);

        storyClient.getEntries(leader.getCoToken(), story.getStoryId(), 0, story.getCurrEntryCharCount());
    }

    @Test(expected = StoryNotFoundException.class)
    public void storyNotFound() throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException,
            ConsecutiveEntryBySameMemberException, SomethingWentWrongException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        storyClient.newEntry(friend.getCoToken(), new ObjectId().toString(), NewEntryRequest.newEntry("blah", story.getCurrEntryCharCount()));
    }

    @Test(expected = NonMemberException.class)
    public void nonMember() throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException,
            SomethingWentWrongException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        UserModel nonMember = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        storyClient.newEntry(nonMember.getCoToken(), story.getStoryId(), NewEntryRequest.newEntry("blah", story.getCurrEntryCharCount()));
    }

    @Test(expected = VersioningException.class)
    public void invalidVersion() throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException,
            ConsecutiveEntryBySameMemberException, SomethingWentWrongException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        storyClient.newEntry(friend.getCoToken(), story.getStoryId(), NewEntryRequest.newEntry("blah", 0));
    }

    @Test(expected = ConsecutiveEntryBySameMemberException.class)
    public void consecutiveEntryBySameMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, VersioningException, StoryNotFoundException,
            NonMemberException, ConsecutiveEntryBySameMemberException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        storyClient.newEntry(leader.getCoToken(), story.getStoryId(), NewEntryRequest.newEntry("blah", story.getCurrEntryCharCount()));
    }

    @Test(expected = BadRequestException.class)
    public void nullVersion() throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException,
            SomethingWentWrongException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        try {
            storyClient.newEntry(leader.getCoToken(), story.getStoryId(), NewEntryRequest.newEntry("blah", null));
        } catch (BadRequestException e) {
            e.getBatchErrors().containsKey("charCountForVersioning");
            throw e;
        }
    }

    @Test(expected = BadRequestException.class)
    public void nullEntry() throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException,
            SomethingWentWrongException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        try {
            storyClient.newEntry(leader.getCoToken(), new ObjectId().toString(), NewEntryRequest.newEntry(null, story.getCurrEntryCharCount()));
        } catch (BadRequestException e) {
            e.getBatchErrors().containsKey("entry");
            throw e;
        }
    }

    @Test(expected = BadRequestException.class)
    public void emptyEntry() throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException,
            SomethingWentWrongException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        try {
            storyClient.newEntry(leader.getCoToken(), new ObjectId().toString(), NewEntryRequest.newEntry("", story.getCurrEntryCharCount()));
        } catch (BadRequestException e) {
            e.getBatchErrors().containsKey("entry");
            throw e;
        }
    }

    @Test(expected = BadRequestException.class)
    public void lessThanMinCharEntry() throws AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException,
            SomethingWentWrongException, BadRequestException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        try {
            storyClient.newEntry(leader.getCoToken(), new ObjectId().toString(), NewEntryRequest.newEntry("a", story.getCurrEntryCharCount()));
        } catch (BadRequestException e) {
            e.getBatchErrors().containsKey("entry");
            throw e;
        }
    }

    @Test(expected = BadRequestException.class)
    public void moreThanMaxCharEntry() throws AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException,
            SomethingWentWrongException, BadRequestException {
        UserModel leader = UserBuilder.createUser();
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse story = storyResponse.getBody();

        try {
            storyClient.newEntry(leader.getCoToken(), new ObjectId().toString(), NewEntryRequest.newEntry(StringUtil.repeat('a', 1001), story.getCurrEntryCharCount()));
        } catch (BadRequestException e) {
            e.getBatchErrors().containsKey("entry");
            throw e;
        }
    }

    private Integer calculateNumCharsFromEntries(List<EntryResponse> entries) {
        int numChars = 0;

        for (int i = 0; i < entries.size(); i++) {
            numChars += entries.get(i).getEntry().length();
        }

        return numChars;
    }

    private List<EntryResponse> getATonOfEntries(UserModel leader, StoryResponse story, Integer beginIndex) throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException,
            StoryNotFoundException, VersioningException, SomethingWentWrongException {

        ResponseEntity<EntriesResponse> entriesResponse = null;
        try {
            entriesResponse = storyClient.getEntries(leader.getCoToken(), story.getStoryId(), beginIndex, story.getCurrEntryCharCount());
        } catch (MoreEntriesLeftException e) {
            List<EntryResponse> entries = e.getEntriesResponse().getEntries();
            entries.addAll(getATonOfEntries(leader, story, e.getEntriesResponse().getNewBeginIndex()));

            return entries;
        }

        return entriesResponse.getBody().getEntries();
    }

    private StoryResponse insertATonOfEntries(UserModel leader, int numCharsPerEntry, int numEntries, int totalEntries) throws SomethingWentWrongException, AuthenticationUnauthorizedException,
            BadRequestException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException {
        UserModel friend = UserBuilder.createUser();

        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(friend).build());
        StoryResponse newStory = storyResponse.getBody();

        String entry = StringUtil.repeat('a', numCharsPerEntry);

        boolean isLeaderTurn = false;
        StoryResponse newEntryResponseStory = newStory;
        for (int i = 0; i < numEntries; i++) {
            if (isLeaderTurn) {
                ResponseEntity<StoryResponse> newEntryResponse = storyClient.newEntry(leader.getCoToken(), newStory.getStoryId(),
                        NewEntryRequest.newEntry(entry, newEntryResponseStory.getCurrEntryCharCount()));
                newEntryResponseStory = newEntryResponse.getBody();
            } else {
                ResponseEntity<StoryResponse> newEntryResponse = storyClient.newEntry(friend.getCoToken(), newStory.getStoryId(),
                        NewEntryRequest.newEntry(entry, newEntryResponseStory.getCurrEntryCharCount()));
                newEntryResponseStory = newEntryResponse.getBody();
            }

            isLeaderTurn = !isLeaderTurn;
        }

        return newEntryResponseStory;
    }
}
