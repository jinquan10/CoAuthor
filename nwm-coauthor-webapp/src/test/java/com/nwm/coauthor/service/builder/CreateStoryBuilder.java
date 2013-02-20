package com.nwm.coauthor.service.builder;

import java.util.ArrayList;
import java.util.List;

import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;

public class CreateStoryBuilder {
	public static CreateStoryRequest createValidStory(List<UserModel> users, int userIndex, List<String> fbFriends, Integer ... numCharacters){
		if(fbFriends == null){
			fbFriends = new ArrayList<String>();
			
			for(int i = 0; i < users.size(); i++){
				if(i != userIndex){
					fbFriends.add(users.get(i).getFbId());
				}
			}			
		}
		
		CreateStoryRequest createStoryRequest = new CreateStoryRequest();
		createStoryRequest.setEntry("12345");
		createStoryRequest.setFbFriends(fbFriends);
		createStoryRequest.setTitle(null);
		
		if(numCharacters.length != 0){
			createStoryRequest.setNumCharacters(numCharacters[0]);
		}else{
			createStoryRequest.setNumCharacters(500);
		}
		
		return createStoryRequest;
	}
}
