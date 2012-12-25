package com.nwm.coauthor.service.builder;

import java.util.ArrayList;
import java.util.List;

import com.nwm.coauthor.service.model.LoginModel;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;

public class CreateStoryBuilder {
	public static CreateStoryRequest createValidStory(List<LoginModel> users, int userIndex){
		List<String> fbFriends = new ArrayList<String>();
		
		for(int i = 0; i < users.size(); i++){
			if(i != userIndex){
				fbFriends.add(users.get(i).getFbId());
			}
		}
		
		CreateStoryRequest createStoryRequest = new CreateStoryRequest();
		createStoryRequest.setEntry("12345");
		createStoryRequest.setFbFriends(fbFriends);
		createStoryRequest.setNumCharacters(5);
		createStoryRequest.setTitle(null);
		
		return createStoryRequest;
	}
}
