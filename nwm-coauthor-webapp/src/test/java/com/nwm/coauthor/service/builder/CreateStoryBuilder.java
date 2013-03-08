package com.nwm.coauthor.service.builder;

import java.util.ArrayList;
import java.util.List;

import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;

public class CreateStoryBuilder {
	
	private CreateStoryRequest request;
	
	public CreateStoryRequest getRequest() {
		return request;
	}

	public void setRequest(CreateStoryRequest request) {
		this.request = request;
	}

	public static CreateStoryBuilder init(){
		CreateStoryBuilder builder = new CreateStoryBuilder();
		builder.setRequest(new CreateStoryRequest());
		
		return builder; 
	}
	
	public CreateStoryBuilder entry(String entry){
		request.setEntry(entry);
		return this;
	}
	
	public CreateStoryBuilder fbFriends(List<String> fbFriends){
		request.setFbFriends(fbFriends);
		return this;
	}

	public CreateStoryBuilder title(String title){
		request.setTitle(title);
		return this;
	}
	
	public CreateStoryBuilder numCharacters(Integer numCharacters){
		request.setNumCharacters(numCharacters);
		return this;
	}
	
	public CreateStoryRequest build(){
		CreateStoryRequest request = new CreateStoryRequest();
		request.setEntry(request.getEntry() == null ? "12345" : request.getEntry());
		request.setFbFriends(request.getFbFriends() == null ? UserBuilder.getDefaultFBFriends() : request.getFbFriends());
//		request.setTitle(request.getTitle() == null ? null : request.getTitle());
		request.setNumCharacters(request.getNumCharacters() == null ? 500 : request.getNumCharacters());
		
		return request;
	}
	
	public static CreateStoryRequest createStoryWithTitle(UserModel leader, List<UserModel> friends, String title){
		CreateStoryRequest request = new CreateStoryRequest();
		request.setEntry("12345");
		request.setFbFriends(getFbFriendIds(friends));
		request.setTitle(title);
		request.setNumCharacters(500);
		
		return request;
	}

	private static List<String> getFbFriendIds(List<UserModel> friends) {
		List<String> fbFriends = new ArrayList<String>();
		
		for(UserModel user : friends){
			fbFriends.add(user.getFbId());
		}
		
		return fbFriends;
	}
	
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
