package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.assertEquals;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnpublishedStoryLikedException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewStory;
import com.nwm.coauthor.service.resource.response.StoryInListResponse;
import com.nwm.coauthor.service.resource.response.StoryInListResponse;

public class LikeTest extends BaseTest {
    @Test
    public void likeAStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AlreadyLikedException, StoryNotFoundException, UserLikingOwnStoryException,
            UserIsNotLeaderException, NoTitleForPublishingException, UnpublishedStoryLikedException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        NewStory storyRequest = NewStoryBuilder.init().title("title").build();
        ResponseEntity<StoryInListResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);

        storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
        ResponseEntity<StoryInListResponse> StoryResponse = storyClient.likeStory(nonMember.getCoToken(), storyResponse.getBody().getStoryId());
        
        StoryInListResponse liked = StoryResponse.getBody();
        assertEquals(new Long(1), liked.getLikes());
    }

    @Test
    public void nonMemberIncrementsLikes() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException,
            AlreadyLikedException, UserLikingOwnStoryException, UnpublishedStoryLikedException, UserIsNotLeaderException, NoTitleForPublishingException, NonMemberException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().title("title").build());

        StoryInListResponse story = storyResponse.getBody();

        storyClient.publishStory(leader.getCoToken(), story.getStoryId());
        ResponseEntity<StoryInListResponse> StoryResponse = storyClient.likeStory(nonMember.getCoToken(), story.getStoryId());

        StoryInListResponse liked = StoryResponse.getBody();
        assertEquals(new Long(1), liked.getLikes());
        
        ResponseEntity<StoryInListResponse> myStoryResponse = storyClient.getMyStory(leader.getCoToken(), story.getStoryId());
        assertEquals(new Long(1), myStoryResponse.getBody().getLikes());
    }

    @Test
    public void memberOfAnotherStoryLikingThisOne() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException,
            AlreadyLikedException, UserLikingOwnStoryException, UnpublishedStoryLikedException, UserIsNotLeaderException, NoTitleForPublishingException, NonMemberException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().title("title").build());

        StoryInListResponse story = storyResponse.getBody();

        storyClient.publishStory(leader.getCoToken(), story.getStoryId());
        storyClient.createStory(nonMember.getCoToken(), NewStoryBuilder.init().title("title").build());
        ResponseEntity<StoryInListResponse> StoryResponse = storyClient.likeStory(nonMember.getCoToken(), story.getStoryId());

        StoryInListResponse liked = StoryResponse.getBody();
        assertEquals(new Long(1), liked.getLikes());        
        
        ResponseEntity<StoryInListResponse> myStoryResponse = storyClient.getMyStory(leader.getCoToken(), story.getStoryId());
        assertEquals(new Long(1), myStoryResponse.getBody().getLikes());
    }
    
    @Test(expected = UnpublishedStoryLikedException.class)
    public void likeAnUnpublishedStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AlreadyLikedException, StoryNotFoundException,
            UserLikingOwnStoryException, UnpublishedStoryLikedException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        NewStory storyRequest = NewStoryBuilder.init().build();
        ResponseEntity<StoryInListResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);

        storyClient.likeStory(nonMember.getCoToken(), storyResponse.getBody().getStoryId());
    }

    @Test(expected = UserLikingOwnStoryException.class)
    public void leaderLikeOwnStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, AlreadyLikedException, StoryNotFoundException,
            UserLikingOwnStoryException, UnpublishedStoryLikedException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> createdStory = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());

        storyClient.likeStory(leader.getCoToken(), createdStory.getBody().getStoryId());
    }

    @Test(expected = UserLikingOwnStoryException.class)
    public void memberLikeOwnStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, AlreadyLikedException, StoryNotFoundException,
            UserLikingOwnStoryException, UnpublishedStoryLikedException {
        UserModel leader = UserBuilder.createUser();
        UserModel member = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> createdStory = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().fbFriendsFromUserModel(member).build());

        storyClient.likeStory(member.getCoToken(), createdStory.getBody().getStoryId());
    }


    @Test(expected = StoryNotFoundException.class)
    public void nonExistantStory() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException,
            UserLikingOwnStoryException, UnpublishedStoryLikedException {
        UserModel user = UserBuilder.createUser();

        storyClient.likeStory(user.getCoToken(), new ObjectId().toString());
    }

    @Test(expected = AuthenticationUnauthorizedException.class)
    public void likeWithNullCoToken() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException,
            AlreadyLikedException, UserLikingOwnStoryException, UnpublishedStoryLikedException {
        UserModel user = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> story = storyClient.createStory(user.getCoToken(), NewStoryBuilder.init().build());
        storyClient.likeStory(null, story.getBody().getStoryId());
    }

    @Test(expected = AuthenticationUnauthorizedException.class)
    public void likeWithEmptyCoToken() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException,
            AlreadyLikedException, UserLikingOwnStoryException, UnpublishedStoryLikedException {
        UserModel user = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> story = storyClient.createStory(user.getCoToken(), NewStoryBuilder.init().build());
        storyClient.likeStory("", story.getBody().getStoryId());
    }

    @Test(expected = AuthenticationUnauthorizedException.class)
    public void likeWithNonExistantCoToken() throws InterruptedException, SomethingWentWrongException,
            AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, AlreadyLikedException, UserLikingOwnStoryException, UnpublishedStoryLikedException {
        UserModel user = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> story = storyClient.createStory(user.getCoToken(), NewStoryBuilder.init().build());
        storyClient.likeStory("asdfasdf", story.getBody().getStoryId());
    }

    @Test(expected = AlreadyLikedException.class)
    public void userLikesAStoryTwice() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException,
            StoryNotFoundException, AlreadyLikedException, UserLikingOwnStoryException, UnpublishedStoryLikedException, UserIsNotLeaderException, NoTitleForPublishingException, NonMemberException {
        UserModel leader = UserBuilder.createUser();
        UserModel nonMember = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> storyResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().title("title").build());

        StoryInListResponse story = storyResponse.getBody();

        storyClient.publishStory(leader.getCoToken(), story.getStoryId());
        storyClient.likeStory(nonMember.getCoToken(), story.getStoryId());
        storyClient.likeStory(nonMember.getCoToken(), story.getStoryId());
    }
}
