package com.nwm.coauthor.service.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AddEntryVersionException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.UnauthorizedException;
import com.nwm.coauthor.exception.WebApplicationException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.StoryEntryResource;
import com.nwm.coauthor.service.resource.request.AddEntryRequest;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.AddEntryResponse;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

public class GetPrivateStoryTest extends TestSetup {
    @Test
    public void createStory_Then_AssertTheSameStoryContents() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException,
            StoryNotFoundException, UnauthorizedException {
        List<UserModel> users = UserBuilder.createUsers(null);
        CreateStoryRequest createStoryRequest = CreateStoryBuilder.createValidStory(users, 0, null);

        ResponseEntity<CreateStoryResponse> createdStory = storyClient.createStory(users.get(0).getCoToken(), createStoryRequest);

        String storyId = createdStory.getBody().getStoryId();

        ResponseEntity<PrivateStoryResponse> privateStory = storyClient.getStoryForEdit(users.get(0).getCoToken(), storyId);

        Assert.assertNotNull(privateStory);
        Assert.assertEquals(HttpStatus.OK, privateStory.getStatusCode());

        PrivateStoryResponse body = privateStory.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.get_id(), storyId);
        Assert.assertEquals(body.getLastFriendEntry(), users.get(0).getFbId());
        Assert.assertEquals(body.getLeaderFbId(), users.get(0).getFbId());
        Assert.assertEquals(body.getTitle(), createStoryRequest.getTitle());
        Assert.assertEquals(body.getEntries().get(0).getEntry(), createStoryRequest.getEntry());
        Assert.assertEquals(body.getFbFriends(), createStoryRequest.getFbFriends());
        Assert.assertEquals(body.getIsPublished(), false);
        Assert.assertEquals(body.getNumCharacters(), createStoryRequest.getNumCharacters());
        Assert.assertEquals(body.getVersion(), new Integer(0));
        Assert.assertEquals(body.getLikes(), new Integer(0));
    }

    @Test
    public void addEntries_AssertAllEntriesAreAdded_InTheRightOrder() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException,
            AddEntryException, StoryNotFoundException, AddEntryVersionException, UnauthorizedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        UserModel user = users.get(0);

        // - Create a story with user0, and add the rest of the users as user0's
        // friends
        ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));

        String storyId = response.getBody().getStoryId();

        List<String> entryFbIds = new ArrayList<String>();
        List<String> entries = new ArrayList<String>();

        int currUser = 1;
        for (int i = 0; i < users.size() * 2; i++) {
            AddEntryRequest request = new AddEntryRequest();
            request.setEntry("hahahaha" + i);
            request.setStoryId(storyId);
            request.setVersion(i);

            if (currUser == users.size()) {
                currUser = 0;
            }

            entryFbIds.add(users.get(currUser).getFbId());
            entries.add(request.getEntry());

            ResponseEntity<AddEntryResponse> entryId = storyClient.addEntry(users.get(currUser++).getCoToken(), request);
            Assert.assertNotNull(entryId);
            Assert.assertEquals(HttpStatus.CREATED, entryId.getStatusCode());
            Assert.assertNotNull(entryId.getBody());
            Assert.assertNotNull(entryId.getBody().getEntryId());
        }

        ResponseEntity<PrivateStoryResponse> privateStory = storyClient.getStoryForEdit(user.getCoToken(), storyId);

        Assert.assertEquals(HttpStatus.OK, privateStory.getStatusCode());
        Assert.assertNotNull(privateStory.getBody());

        PrivateStoryResponse privateBody = privateStory.getBody();
        List<StoryEntryResource> privateEntries = privateBody.getEntries();

        for (int i = 0; i < privateEntries.size(); i++) {
            if (i == 0) {
                continue;
            }

            Assert.assertEquals(privateEntries.get(i).getEntry(), entries.get(i - 1));
            Assert.assertEquals(privateEntries.get(i).getFbId(), entryFbIds.get(i - 1));
        }
    }

    @Test(expected = WebApplicationException.class)
    public void withEmptyStoryId() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UnauthorizedException {
        List<UserModel> users = UserBuilder.createUsers(null);
        CreateStoryRequest createStoryRequest = CreateStoryBuilder.createValidStory(users, 0, null);

        ResponseEntity<CreateStoryResponse> createdStory = storyClient.createStory(users.get(0).getCoToken(), createStoryRequest);

        String storyId = createdStory.getBody().getStoryId();

        ResponseEntity<PrivateStoryResponse> privateStory = storyClient.getStoryForEdit(users.get(0).getCoToken(), "");
    }

    @Test(expected = BadRequestException.class)
    public void withInvalidStoryId() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, InterruptedException, UnauthorizedException {
        List<UserModel> users = UserBuilder.createUsers(null);
        CreateStoryRequest createStoryRequest = CreateStoryBuilder.createValidStory(users, 0, null);

        ResponseEntity<CreateStoryResponse> createdStory = storyClient.createStory(users.get(0).getCoToken(), createStoryRequest);

        String storyId = createdStory.getBody().getStoryId();

        ResponseEntity<PrivateStoryResponse> privateStory = storyClient.getStoryForEdit(users.get(0).getCoToken(), "haha");
    }

    @Test(expected = StoryNotFoundException.class)
    public void withNonExistantStoryId() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, InterruptedException,
            UnauthorizedException {
        List<UserModel> users = UserBuilder.createUsers(null);
        CreateStoryRequest createStoryRequest = CreateStoryBuilder.createValidStory(users, 0, null);

        ResponseEntity<CreateStoryResponse> createdStory = storyClient.createStory(users.get(0).getCoToken(), createStoryRequest);

        String storyId = createdStory.getBody().getStoryId();

        storyId = "1" + storyId.substring(1);

        ResponseEntity<PrivateStoryResponse> privateStory = storyClient.getStoryForEdit(users.get(0).getCoToken(), storyId);
    }

    @Test(expected = UnauthorizedException.class)
    public void withAUserThatDoesNotBelongToStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, InterruptedException,
            UnauthorizedException {
        List<UserModel> users = UserBuilder.createUsers(3);

        List<String> fbFriends = new ArrayList<String>();
        fbFriends.add(users.get(1).getFbId());

        CreateStoryRequest createStoryRequest = CreateStoryBuilder.createValidStory(users, 0, fbFriends);
        ResponseEntity<CreateStoryResponse> createdStory = storyClient.createStory(users.get(0).getCoToken(), createStoryRequest);
        String storyId = createdStory.getBody().getStoryId();

        storyClient.getStoryForEdit(users.get(2).getCoToken(), storyId);
    }
}
