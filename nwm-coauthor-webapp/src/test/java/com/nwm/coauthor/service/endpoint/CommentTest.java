package com.nwm.coauthor.service.endpoint;

import org.junit.Test;

import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.MemberOrLeaderException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.StoryNotPublishedYetException;

public class CommentTest extends BaseTest{
	@Test(expected = StoryNotFoundException.class)
	public void nonExistantStory(){
		
	}
	
	@Test(expected = BadRequestException.class)
	public void nullComment(){
		
	}
	
	@Test(expected = BadRequestException.class)
	public void lessThanMinCharactersComment(){
		
	}
	
	@Test(expected = BadRequestException.class)
	public void largerThanCharactersComment(){
		
	}
	
	@Test(expected = StoryNotPublishedYetException.class)
	public void notPublishedYet(){
		
	}
	
	@Test(expected = MemberOrLeaderException.class)
	public void memberOrLeader(){
		
	}
	
	@Test
	public void comment(){
		
	}
}
