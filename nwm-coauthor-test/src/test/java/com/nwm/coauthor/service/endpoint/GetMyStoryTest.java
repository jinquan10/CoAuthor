package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class GetMyStoryTest extends BaseTest {
    @Test
    public void getMyStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, NonMemberException {
        UserModel leader = UserBuilder.createUser();

        NewStoryRequest newStoryRequest = NewStoryBuilder.init().build();

        ResponseEntity<StoryResponse> createdStoryResponse = storyClient.createStory(leader.getCoToken(), newStoryRequest);
        StoryResponse createdStory = createdStoryResponse.getBody();

        String storyId = createdStory.getStoryId();

        ResponseEntity<StoryResponse> myStoryResponse = storyClient.getMyStory(leader.getCoToken(), storyId);

        assertNotNull(myStoryResponse);
        assertEquals(HttpStatus.OK, myStoryResponse.getStatusCode());

        StoryResponse myStory = myStoryResponse.getBody();

        assertNotNull(myStory);
        assertEquals(myStory.getStoryId(), storyId);
        assertEquals(myStory.getLastFriendWithEntry(), leader.getFbId());
        assertEquals(myStory.getLeaderFbId(), leader.getFbId());
        assertEquals(myStory.getTitle(), newStoryRequest.getTitle());
        assertEquals(myStory.getLastEntry(), newStoryRequest.getEntry());
        assertEquals(myStory.getFbFriends(), newStoryRequest.getFbFriends());
        assertEquals(myStory.getIsPublished(), false);
        assertEquals(myStory.getCurrEntryCharCount(), createdStory.getCurrEntryCharCount());
        assertEquals(myStory.getLikes(), createdStory.getLikes());
        assertEquals(myStory.getStoryLastUpdated(), createdStory.getStoryLastUpdated());
    }

    @Test
    // - actually calls getMyStories, because of SpringMVC
    public void withEmptyStoryId() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, NonMemberException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryResponse> myStoryResponse = storyClient.getMyStory(leader.getCoToken(), "");

        assertNull(myStoryResponse.getBody().getStoryId());
    }

    @Test(expected = StoryNotFoundException.class)
    public void withNonExistantStoryId() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, NonMemberException {
        UserModel leader = UserBuilder.createUser();

        storyClient.getMyStory(leader.getCoToken(), new ObjectId().toString());
    }

    @Test(expected = NonMemberException.class)
    public void nonMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, NonMemberException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();
        
        NewStoryRequest newStoryRequest = NewStoryBuilder.init().build();

        ResponseEntity<StoryResponse> createdStoryResponse = storyClient.createStory(leader.getCoToken(), newStoryRequest);
        StoryResponse createdStory = createdStoryResponse.getBody();

        String storyId = createdStory.getStoryId();

        storyClient.getMyStory(nonMember.getCoToken(), storyId);
    }
}
