package com.nwm.coauthor.service.builder;

import java.util.ArrayList;
import java.util.List;

import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;

public class NewStoryBuilder {

    private NewStoryRequest request;
    private List<String> fbFriends;

    public NewStoryRequest getRequest() {
        return request;
    }

    public void setRequest(NewStoryRequest request) {
        this.request = request;
    }

    public static NewStoryBuilder init() {
        NewStoryBuilder builder = new NewStoryBuilder();
        builder.setRequest(new NewStoryRequest());

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
    	List<String> fbFriends = new ArrayList<String>();
		String fbId = friend.getFbId();
		fbFriends.add(fbId);
		
		request.setFbFriends(fbFriends);
		
		return this;
    }
    
    public NewStoryBuilder title(String title) {
        request.setTitle(title);
        return this;
    }

    
    public NewStoryRequest build() {
        request.setEntry(request.getEntry() == null ? "12345" : request.getEntry());
        request.setFbFriends(request.getFbFriends() == null ? (setFbFriends(UserBuilder.getDefaultFBFriends())) : request.getFbFriends());
        request.setTitle(request.getTitle() == null ? null : request.getTitle());

        return request;
    }

    public NewStoryRequest buildBare() {
        return new NewStoryRequest();
    }
    
    private static List<String> getFbFriendIds(List<UserModel> friends) {
        List<String> fbFriends = new ArrayList<String>();

        for (UserModel user : friends) {
            fbFriends.add(user.getFbId());
        }

        return fbFriends;
    }

    public List<String> getFbFriends() {
        return fbFriends;
    }

    public List<String> setFbFriends(List<String> fbFriends) {
        this.fbFriends = fbFriends;
        return fbFriends;
    }
}
