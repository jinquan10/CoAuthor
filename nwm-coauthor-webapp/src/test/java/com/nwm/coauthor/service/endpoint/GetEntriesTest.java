package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class GetEntriesTest extends BaseTest {
    @Test
    public void zeroEntriesRightAfterNewStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, CannotGetEntriesException, StoryNotFoundException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse newStory = newStoryResponse.getBody();

        ResponseEntity<EntriesResponse> entriesResponse = storyClient.getEntries(leader.getCoToken(), newStory.getStoryId(), newStory.getCurrEntryCount());
        EntriesResponse entries = entriesResponse.getBody();

        assertEquals(0, entries.getEntries().size());
    }

    @Test(expected = StoryNotFoundException.class)
    public void storyNotFound() throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException, SomethingWentWrongException, StoryNotFoundException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse newStory = newStoryResponse.getBody();

        storyClient.getEntries(leader.getCoToken(), new ObjectId().toString(), newStory.getCurrEntryCount());
    }
    
    @Test
    public void oneEntryRightAfterNewStory(){
        
    }
}
