package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class AuthenticationResponse extends BaseResource {
	private String token;

	public AuthenticationResponse(){
		
	}
	
	public AuthenticationResponse(String token){
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
