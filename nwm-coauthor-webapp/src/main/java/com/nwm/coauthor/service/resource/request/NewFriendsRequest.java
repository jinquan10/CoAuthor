package com.nwm.coauthor.service.resource.request;

import java.util.ArrayList;
import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class NewFriendsRequest extends BaseResource{
    private List<String> newFriends;

    private NewFriendsRequest(){
        newFriends = new ArrayList<String>();
    }
    
    public static NewFriendsRequest init(){
        return new NewFriendsRequest();
    }
    
    public NewFriendsRequest addNewFriend(String fbId){
        newFriends.add(fbId);
        return this;
    }
    
    public List<String> getNewFriends() {
        return newFriends;
    }

    public void setNewFriends(List<String> newFriends) {
        this.newFriends = newFriends;
    }
}
