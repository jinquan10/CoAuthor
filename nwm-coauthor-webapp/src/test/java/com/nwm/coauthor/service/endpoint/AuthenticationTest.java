package com.nwm.coauthor.service.endpoint;

import org.junit.Assert;
import org.junit.Test;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.client.AuthenticationClient;
import com.nwm.coauthor.service.resource.request.AuthenticationRequest;
import com.nwm.coauthor.service.resource.response.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthenticationTest extends TestSetup{
	private final AuthenticationClient client = new AuthenticationClient();
	
	@Test
	public void authenticateTest() throws SomethingWentWrongException{
		AuthenticationRequest authRequest = new AuthenticationRequest("username", "password"); 
		
		ResponseEntity<AuthenticationResponse> response = client.authenticate(authRequest);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(response.getBody().getToken(), "This will be a token returned by validation username/password");
	}
}
