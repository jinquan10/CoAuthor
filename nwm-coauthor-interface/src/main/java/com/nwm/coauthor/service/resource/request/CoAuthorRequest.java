package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.resource.BaseResource;

public class CoAuthorRequest extends BaseResource{
	private String coToken;

	public String getCoToken() {
		return coToken;
	}

	public void setCoToken(String coToken) {
		this.coToken = coToken;
	}
}
