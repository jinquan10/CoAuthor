package com.nwm.coauthor.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.resource.request.AuthenticateFBRequest;
import com.nwm.coauthor.service.resource.response.AuthenticationResponse;

@Controller
@RequestMapping(value = "/authenticate", produces = "application/json", consumes = "application/json")
public class AuthenticationControllerImpl extends BaseControllerImpl implements AuthenticationController{
	@Autowired
	private AuthenticationManagerImpl authenticationManager = null;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> authenticateFB(@RequestBody AuthenticateFBRequest authResource) throws SomethingWentWrongException {
		String coToken = authenticationManager.authenticateFB(authResource.getFbToken());
	
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(coToken), HttpStatus.OK);
	}
}
