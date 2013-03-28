package com.nwm.coauthor.service.endpoint;

import org.junit.Test;

import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.VersioningException;

public class NewEntryTest extends BaseTest {
    @Test
    public void newEntry(){
        
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
