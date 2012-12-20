package com.nwm.coauthor.service.controller;

import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.resource.request.AuthenticationRequest;
import com.nwm.coauthor.service.resource.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/authenticate", produces = "application/json", consumes = "application/json")
public class AuthenticationControllerImpl extends BaseControllerImpl implements AuthenticationController{
	@Autowired
	private AuthenticationManagerImpl authenticationManager = null;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authResource) throws SomethingWentWrongException {
		// - Validate request resource here
		String token = authenticationManager.authenticateUsernamePassword(authResource.getUsername(), authResource.getPassword());
	
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(token), HttpStatus.OK);
	}
}
