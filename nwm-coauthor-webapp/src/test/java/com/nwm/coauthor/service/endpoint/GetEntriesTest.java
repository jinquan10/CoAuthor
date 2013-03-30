package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.MoreEntriesLeftException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class GetEntriesTest extends BaseTest {
    @Test
    public void getZeroEntriesRightAfterNewStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, CannotGetEntriesException, StoryNotFoundException, VersioningException, MoreEntriesLeftException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse newStory = newStoryResponse.getBody();

        ResponseEntity<EntriesResponse> entriesResponse = storyClient.getEntries(leader.getCoToken(), newStory.getStoryId(), newStory.getCurrEntryCharCount(), newStory.getCurrEntryCharCount());
        EntriesResponse entries = entriesResponse.getBody();

        assertEquals(0, entries.getEntries().size());
    }

    @Test
    public void getOneEntryRightAfterNewStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, CannotGetEntriesException, StoryNotFoundException, VersioningException, MoreEntriesLeftException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse newStory = newStoryResponse.getBody();

        ResponseEntity<EntriesResponse> entriesResponse = storyClient.getEntries(leader.getCoToken(), newStory.getStoryId(), 0, newStory.getCurrEntryCharCount());
        EntriesResponse entries = entriesResponse.getBody();

        assertEquals(1, entries.getEntries().size());
    }

    @Test
    public void nonMemberWhenPublished() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, CannotGetEntriesException, StoryNotFoundException, VersioningException, MoreEntriesLeftException, UserIsNotLeaderException, NoTitleForPublishingException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        ResponseEntity<StoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().title("title").build());
        StoryResponse newStory = newStoryResponse.getBody();

        storyClient.publishStory(leader.getCoToken(), newStory.getStoryId());
        
        ResponseEntity<EntriesResponse> entriesResponse = storyClient.getEntries(nonMember.getCoToken(), newStory.getStoryId(), 0, newStory.getCurrEntryCharCount());
        EntriesResponse entries = entriesResponse.getBody();
        
        assertNotNull(entries);
        assertEquals(1, entries.getEntries().size());
    }
    
    @Test(expected = StoryNotFoundException.class)
    public void storyNotFound() throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException, SomethingWentWrongException, StoryNotFoundException, VersioningException, MoreEntriesLeftException {
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse newStory = newStoryResponse.getBody();
        
        storyClient.getEntries(leader.getCoToken(), new ObjectId().toString(), newStory.getCurrEntryCharCount(), newStory.getCurrEntryCharCount());
    }
    
    @Test(expected = CannotGetEntriesException.class)
    public void nonMemberWhenNotPublished() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, CannotGetEntriesException, StoryNotFoundException, VersioningException, MoreEntriesLeftException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        ResponseEntity<StoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse newStory = newStoryResponse.getBody();

        storyClient.getEntries(nonMember.getCoToken(), newStory.getStoryId(), 0, newStory.getCurrEntryCharCount());
    }
    
    @Test(expected = VersioningException.class)
    public void incorrectChars() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, CannotGetEntriesException, StoryNotFoundException, VersioningException, MoreEntriesLeftException{
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse newStory = storyResponse.getBody();
        
        storyClient.getEntries(leader.getCoToken(), newStory.getStoryId(), 0, 0);
    }
}
