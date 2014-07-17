package com.nwm.coauthor.service.builder;

import java.util.ArrayList;
import java.util.List;

import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewStory;

public class NewStoryBuilder {

    private NewStory request;

    public NewStory getRequest() {
        return request;
    }

    public void setRequest(NewStory request) {
        this.request = request;
    }

    public static NewStoryBuilder init() {
        NewStoryBuilder builder = new NewStoryBuilder();
        builder.setRequest(new NewStory());
        
        return builder;
    }

    public NewStoryBuilder entry(String entry) {
        request.setEntry(entry);
        return this;
    }

    public NewStoryBuilder fbIdList(List<String> fbFriends) {
        request.setFbFriends(fbFriends);
        return this;
    }

    public NewStoryBuilder fbFriendsFromUserModel(UserModel friend) {
		String fbId = friend.getFbId();

		if(getRequest().getFbFriends() == null){
		    getRequest().setFbFriends(new ArrayList<String>());
		}
		
		request.getFbFriends().add(fbId);
		
		return this;
    }
    
    public NewStoryBuilder title(String title) {
        request.setTitle(title);
        return this;
    }

    
    public NewStory build() {
        request.setEntry(request.getEntry() == null ? "12345" : request.getEntry());
        request.setFbFriends(request.getFbFriends() == null ? (UserBuilder.getDefaultFBFriends()) : request.getFbFriends());
        request.setTitle(request.getTitle() == null ? null : request.getTitle());

        return request;
    }

    public NewStory buildBare() {
        return new NewStory();
    }
}
