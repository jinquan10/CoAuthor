package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.resource.BaseResource;

public class AuthenticateFBRequest extends BaseResource {
	private String fbToken;

	public AuthenticateFBRequest() {

	}

	public AuthenticateFBRequest(String fbToken) {
		this.fbToken = fbToken;
	}

	public String getFbToken() {
		return fbToken;
	}

	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}
}
