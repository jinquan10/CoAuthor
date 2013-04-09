package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.*;

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
import com.nwm.coauthor.service.resource.response.StoryResponse;

public class RateStoryTest extends BaseTest{
    @Test
    public void rateStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, NonMemberException{
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<StoryResponse> createStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        StoryResponse storyResponse = createStoryResponse.getBody();
        
        int numRatings = 5;
        double averageRating = 0.d;
        
        for(int i = 0; i < numRatings; i++){
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
    public void alreadyRated(){
        
    }
    
    @Test(expected = StoryNotFoundException.class)
    public void storyNotFound(){
        
    }
    
    @Test
    public void emptyRatingString(){
        
    }
    
    @Test
    public void nonNumberRatingString(){
        
    }
    
    @Test(expected = BadRequestException.class)
    public void lessThanMinRating(){
        
    }
    
    @Test(expected = BadRequestException.class)
    public void moreThanMaxRating(){
        
    }
    
    @Test(expected = MemberOrLeaderException.class)
    public void memberOrLeader(){
        
    }
}
