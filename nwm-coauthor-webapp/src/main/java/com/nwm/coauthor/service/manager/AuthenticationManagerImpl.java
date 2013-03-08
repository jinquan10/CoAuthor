package com.nwm.coauthor.service.manager;

import java.util.UUID;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.service.client.FacebookClientImpl;
import com.nwm.coauthor.service.dao.AuthenticationDAOImpl;
import com.nwm.coauthor.service.model.FBUser;
import com.nwm.coauthor.service.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerImpl {
    @Autowired
    private AuthenticationDAOImpl authenticationDAO;
    @Autowired
    private FacebookClientImpl fbClient;

    /**
     * 
     * @param coToken
     * @return mongo objectId
     * @throws AuthenticationUnauthorizedException
     */
    public String authenticateCOTokenForFbId(String coToken) throws AuthenticationUnauthorizedException {
        UserModel loginModel = authenticationDAO.authenticateCOTokenForFbId(coToken);

        if (loginModel == null) {
            throw new AuthenticationUnauthorizedException();
        }

        return loginModel.getFbId();
    }

    public String authenticateFB(String fbToken) throws AuthenticationUnauthorizedException {
        FBUser fbUser = fbClient.validate(fbToken);
        String coToken = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();

        authenticationDAO.login(coToken, fbUser.getId());

        return coToken;
    }
}
