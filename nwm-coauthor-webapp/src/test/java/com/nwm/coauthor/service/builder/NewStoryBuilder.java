package com.nwm.coauthor.service.builder;

import java.util.ArrayList;
import java.util.List;

import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;

public class NewStoryBuilder {

    private NewStoryRequest request;

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

    public NewStoryBuilder fbFriends(List<String> fbFriends) {
        request.setFbFriends(fbFriends);
        return this;
    }

    public NewStoryBuilder title(String title) {
        request.setTitle(title);
        return this;
    }

    public NewStoryBuilder numCharacters(Integer numCharacters) {
        request.setNumCharacters(numCharacters);
        return this;
    }

    public NewStoryRequest build() {
        request.setEntry(request.getEntry() == null ? "12345" : request.getEntry());
        request.setFbFriends(request.getFbFriends() == null ? UserBuilder.getDefaultFBFriends() : request.getFbFriends());
        request.setTitle(request.getTitle() == null ? null : request.getTitle());
        request.setNumCharacters(request.getNumCharacters() == null ? 500 : request.getNumCharacters());

        return request;
    }

    private static List<String> getFbFriendIds(List<UserModel> friends) {
        List<String> fbFriends = new ArrayList<String>();

        for (UserModel user : friends) {
            fbFriends.add(user.getFbId());
        }

        return fbFriends;
    }

    public static NewStoryRequest createValidStory(List<UserModel> users, int userIndex, List<String> fbFriends, Integer... numCharacters) {
        if (fbFriends == null) {
            fbFriends = new ArrayList<String>();

            for (int i = 0; i < users.size(); i++) {
                if (i != userIndex) {
                    fbFriends.add(users.get(i).getFbId());
                }
            }
        }

        NewStoryRequest createStoryRequest = new NewStoryRequest();
        createStoryRequest.setEntry("12345");
        createStoryRequest.setFbFriends(fbFriends);
        createStoryRequest.setTitle(null);

        if (numCharacters.length != 0) {
            createStoryRequest.setNumCharacters(numCharacters[0]);
        } else {
            createStoryRequest.setNumCharacters(500);
        }

        return createStoryRequest;
    }
}
