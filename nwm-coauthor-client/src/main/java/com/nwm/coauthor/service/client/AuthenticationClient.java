package com.nwm.coauthor.service.client;

import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.controller.AuthenticationController;
import com.nwm.coauthor.service.resource.request.AuthenticationRequest;
import com.nwm.coauthor.service.resource.response.AuthenticationResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class AuthenticationClient extends BaseClient implements AuthenticationController{
	private static final String AUTHENTICATE_ENDPOINT = "/authenticate";
	
	public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authRequest) throws SomethingWentWrongException {
		return restTemplate.exchange(urlResolver(AUTHENTICATE_ENDPOINT), HttpMethod.POST, httpEntity(authRequest), AuthenticationResponse.class);
	}
}
