package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class CreateStoryResponse extends BaseResource {
	private String storyId;

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public CreateStoryResponse() {

	}
	
	public CreateStoryResponse(String storyId) {
		this.storyId = storyId;
	}
}
