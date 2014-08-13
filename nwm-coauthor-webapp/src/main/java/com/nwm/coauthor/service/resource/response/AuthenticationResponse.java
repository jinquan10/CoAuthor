package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class AuthenticationResponse extends BaseResource {
	private String coToken;

	public AuthenticationResponse(){
		
	}
	
	public AuthenticationResponse(String coToken){
		this.setCoToken(coToken);
	}

	public String getCoToken() {
		return coToken;
	}

	public void setCoToken(String coToken) {
		this.coToken = coToken;
	}
}
