package com.nwm.coauthor.service.resource.response;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class NewFriendsResponse extends BaseResource{
    private List<String> newFriends;

    public List<String> getNewFriends() {
        return newFriends;
    }

    public void setNewFriends(List<String> newFriends) {
        this.newFriends = newFriends;
    }
}
