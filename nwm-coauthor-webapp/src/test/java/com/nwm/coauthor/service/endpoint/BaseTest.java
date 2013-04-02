package com.nwm.coauthor.service.endpoint;

import com.nwm.coauthor.service.client.AuthenticationClient;
import com.nwm.coauthor.service.client.StoryClient;

public class BaseTest {
    protected StoryClient storyClient = StoryClient.instance();
    protected AuthenticationClient authenticationClient = AuthenticationClient.instance();

    // private static void authenticateForCoToken() throws
    // SomethingWentWrongException, AuthenticationUnauthorizedException{
    // AuthenticationClient client = new AuthenticationClient();
    // String fbToken = System.getProperty("fbToken");
    //
    // ResponseEntity<AuthenticationResponse> response =
    // client.authenticateFB(new AuthenticateFBRequest(fbToken));

    // users = new ArrayList<LoginModel>();
    // coTokens.add(response.getBody().getCoToken());
    // }

}
