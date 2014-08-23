package com.nwm.coauthor.service.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nwm.coauthor.Constants;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.UsernameExistsException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.resource.request.LogoutRequest;
import com.nwm.coauthor.service.resource.request.NativeAuthRequest;
import com.nwm.coauthor.service.resource.response.AuthedResponse;

@Controller
@RequestMapping(produces = "application/json")
public class AuthController extends BaseControllerImpl {
    @Autowired
    private AuthenticationManagerImpl authenticationManager;
    
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = Constants.CREATE_NATIVE_USER_PATH, method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<AuthedResponse> createNativeUser(@RequestHeader("TimeZoneOffsetMinutes") Long timeZoneOffsetMinutes, @RequestBody NativeAuthRequest creds) throws NoSuchAlgorithmException, UnsupportedEncodingException, UsernameExistsException {
        return new ResponseEntity<AuthedResponse>(authenticationManager.createNativeUser(creds, timeZoneOffsetMinutes), HttpStatus.CREATED);
    }
    
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = Constants.LOGIN_NATIVE_PATH, method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<AuthedResponse> login(@RequestBody NativeAuthRequest creds) throws NoSuchAlgorithmException, UnsupportedEncodingException, UsernameExistsException, AuthenticationUnauthorizedException {
        return new ResponseEntity<AuthedResponse>(authenticationManager.loginNative(creds), HttpStatus.OK);
    }
    
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = Constants.LOGOUT_PATH, method = RequestMethod.POST, consumes = "application/json")
    public void logout(@RequestBody LogoutRequest logoutReq) {
        authenticationManager.logout(logoutReq);
    }
}
