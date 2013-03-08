package com.nwm.coauthor.service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.service.model.FBUser;

@Component
public class FacebookClientImpl {
    private String fbValidationUrl = "https://graph.facebook.com/me?access_token=";
    private RestTemplate restTemplate = new RestTemplate();

    public FBUser validate(String fbToken) throws AuthenticationUnauthorizedException {
        FBUser fbUser = null;
        try {
            fbUser = restTemplate.getForObject(fbValidationUrl + fbToken, FBUser.class);
        } catch (Throwable e) {
            throw new AuthenticationUnauthorizedException();
        } finally {
            if (fbUser == null) {
                throw new AuthenticationUnauthorizedException();
            }
        }

        return fbUser;
    }
}
