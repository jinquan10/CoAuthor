package com.nwm.coauthor.service.manager;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.UsernameExistsException;
import com.nwm.coauthor.service.client.FacebookClientImpl;
import com.nwm.coauthor.service.dao.AuthenticationDAOImpl;
import com.nwm.coauthor.service.model.FBUser;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.LogoutRequest;
import com.nwm.coauthor.service.resource.request.NativeAuthRequest;
import com.nwm.coauthor.service.resource.response.AuthedResponse;
import com.nwm.coauthor.util.Utils;

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

        return null;
    }

    public String authenticateFB(String fbToken) throws AuthenticationUnauthorizedException {
        FBUser fbUser = fbClient.validate(fbToken);
        String coToken = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();

        authenticationDAO.login(coToken, fbUser.getId());

        return coToken;
    }
    
    public AuthedResponse createNativeUser(NativeAuthRequest creds, Long timeZoneOffsetMinutes) throws NoSuchAlgorithmException, UnsupportedEncodingException, UsernameExistsException {
        UserModel model = new UserModel();

        String token = UUID.randomUUID().toString();
        model.setCoToken(Utils.encrypt(token));
        model.setUsername(creds.getUsername());
        model.setPassword(Utils.encrypt(creds.getPassword()));
        model.setTimeZoneOffsetMinutes(timeZoneOffsetMinutes);
        
        authenticationDAO.insertNativeUser(model);
        
        return new AuthedResponse(token, creds.getUsername());
    }

    public AuthedResponse loginNative(NativeAuthRequest creds) throws AuthenticationUnauthorizedException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String token = UUID.randomUUID().toString();
        
        authenticationDAO.loginNative(creds.getUsername(), Utils.encrypt(creds.getPassword()), Utils.encrypt(token));
        
        return new AuthedResponse(token, creds.getUsername());
    }
    
    public void authenticateNative(String coToken) throws AuthenticationUnauthorizedException, NoSuchAlgorithmException, UnsupportedEncodingException {
        authenticationDAO.authenticateNative(Utils.encrypt(coToken));
    }

    public void logout(LogoutRequest logoutReq) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        authenticationDAO.logout(Utils.encrypt(logoutReq.getCoToken()));
    }
}
