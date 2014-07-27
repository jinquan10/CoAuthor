package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyRatedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.MemberOrLeaderException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.response.StoryInListResponse;

public class RateStoryTest extends BaseTest {
    @Test
    public void rateStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, NonMemberException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryInListResponse storyResponse = createStoryResponse.getBody();

        int numRatings = 5;
        double averageRating = 0.d;

        for (int i = 0; i < numRatings; i++) {
            UserModel nonMember = UserBuilder.createUser();

            int initRandom = (int) (Math.random() * 100);
            int rating = (initRandom % 5) + 1;
            averageRating = ((numRatings * averageRating) + (rating)) / (numRatings + 1);

            createStoryResponse = storyClient.rateStory(nonMember.getCoToken(), storyResponse.getStoryId(), rating);
            storyResponse = createStoryResponse.getBody();

            assertEquals(averageRating, storyResponse.getRating().doubleValue(), 0.05d);
        }
    }

    @Test(expected = AlreadyRatedException.class)
    public void alreadyRated() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, NonMemberException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryInListResponse storyResponse = createStoryResponse.getBody();
        
        UserModel nonMember = UserBuilder.createUser();
        
        storyClient.rateStory(nonMember.getCoToken(), storyResponse.getStoryId(), 5);
        storyClient.rateStory(nonMember.getCoToken(), storyResponse.getStoryId(), 5);
    }

    @Test(expected = StoryNotFoundException.class)
    public void storyNotFound() throws SomethingWentWrongException, NonMemberException, AuthenticationUnauthorizedException, BadRequestException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryInListResponse storyResponse = createStoryResponse.getBody();
        
        UserModel nonMember = UserBuilder.createUser();
        
        storyClient.rateStory(nonMember.getCoToken(), new ObjectId().toString(), 5);        
    }

    @Ignore
    @Test
    public void emptyRatingString() throws SomethingWentWrongException, NonMemberException, AuthenticationUnauthorizedException, BadRequestException {

    }

    @Ignore
    @Test
    public void nonNumberRatingString() {

    }

    @Test(expected = BadRequestException.class)
    public void lessThanMinRating() throws SomethingWentWrongException, NonMemberException, AuthenticationUnauthorizedException, BadRequestException{
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryInListResponse storyResponse = createStoryResponse.getBody();
        
        UserModel nonMember = UserBuilder.createUser();
        
        try{
            storyClient.rateStory(nonMember.getCoToken(), new ObjectId().toString(), 0);
        }catch(BadRequestException e){
            assertTrue(e.getBatchErrors().containsKey("rating"));
            
            throw e;
        }
    }

    @Test(expected = BadRequestException.class)
    public void moreThanMaxRating() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryInListResponse storyResponse = createStoryResponse.getBody();
        
        UserModel nonMember = UserBuilder.createUser();
        
        try{
            storyClient.rateStory(nonMember.getCoToken(), new ObjectId().toString(), 6);
        }catch(BadRequestException e){
            assertTrue(e.getBatchErrors().containsKey("rating"));
            
            throw e;
        }
    }

    @Test(expected = MemberOrLeaderException.class)
    public void memberOrLeaderMember() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
        UserModel leader = UserBuilder.createUser();

        ResponseEntity<StoryInListResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryInListResponse storyResponse = createStoryResponse.getBody();
        
        UserModel nonMember = UserBuilder.createUser();
    }
}
