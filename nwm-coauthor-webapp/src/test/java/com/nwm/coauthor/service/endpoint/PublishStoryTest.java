package com.nwm.coauthor.service.endpoint;

import org.junit.Test;

import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;

public class PublishStoryTest extends TestSetup{
	@Test(expected = UserIsNotLeaderException.class)
	public void userIsNotLeader(){
		// - create story with title
		// - publish it as a member
	}
	
	@Test(expected = UserIsNotLeaderException.class)
	public void userIsANonMember(){
		// - create story with title
		// - publish it as a nonMember
	}
	
	@Test(expected = NoTitleForPublishingException.class)
	public void hasNoTitle(){
		
	}
}
