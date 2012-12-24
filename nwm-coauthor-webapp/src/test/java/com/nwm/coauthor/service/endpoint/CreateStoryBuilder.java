package com.nwm.coauthor.service.endpoint;

import java.util.ArrayList;
import java.util.List;

import com.nwm.coauthor.service.resource.request.CreateStoryRequest;

public class CreateStoryBuilder {
	public static CreateStoryRequest createValidStory(){
		List<String> fbFriends = new ArrayList<String>();
		fbFriends.add("100000029500725");
		
		CreateStoryRequest createStoryRequest = new CreateStoryRequest();
		createStoryRequest.setEntry("12345");
		createStoryRequest.setFbFriends(fbFriends);
		createStoryRequest.setNumCharacters(5);
		createStoryRequest.setTitle(null);
		
		return createStoryRequest;
	}
}
