package com.nwm.coauthor.service.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.nwm.coauthor.service.model.UserModel;

public class UserBuilder {
    public static List<UserModel> createUsers(Integer numUsers) throws InterruptedException {
        if (numUsers == null)
            numUsers = 2;

        List<UserModel> users = new ArrayList<UserModel>();
        String numUsersStr = System.getProperty("numUsers");

        if (StringUtils.hasText(numUsersStr)) {
            numUsers = Integer.parseInt(numUsersStr);
        }

        for (int i = 0; i < numUsers; i++) {
            UserModel loginModel = new UserModel(String.valueOf(Math.random()), String.valueOf(Math.random()));
            users.add(loginModel);
            MongoInstance.getInstance().insert(loginModel);
        }

        return users;
    }

    public static UserModel createUser() {
        return new UserModel(String.valueOf(Math.random()), String.valueOf(Math.random()));
    }

    public static List<String> getFBFriends(Integer numFriends) {
        List<String> fbFriends = new ArrayList<String>();

        for (int i = 0; i < numFriends; i++) {
            UserModel loginModel = new UserModel(String.valueOf(Math.random()), String.valueOf(Math.random()));
            fbFriends.add(loginModel.getFbId());
            MongoInstance.getInstance().insert(loginModel);
        }

        return fbFriends;
    }

    public static List<String> getDefaultFBFriends() {
        int numFriends = 2;

        String numUsersStr = System.getProperty("numFriends");
        if (StringUtils.hasText(numUsersStr)) {
            numFriends = Integer.parseInt(numUsersStr);
        }

        return getFBFriends(numFriends);
    }
}
