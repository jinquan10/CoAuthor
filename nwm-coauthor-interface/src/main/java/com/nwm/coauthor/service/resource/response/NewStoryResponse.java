package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class NewStoryResponse extends BaseResource {
	private String storyId;
	private Long storyLastUpdated;

	public static NewStoryResponse newStoryResponse(String storyId, Long storyLastUpdated){
		NewStoryResponse newStoryResponse = new NewStoryResponse();
		
		newStoryResponse.setStoryId(storyId);
		newStoryResponse.setStoryLastUpdated(storyLastUpdated);
		
		return newStoryResponse;
	}
	
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	public Long getStoryLastUpdated() {
		return storyLastUpdated;
	}
	public void setStoryLastUpdated(Long storyLastUpdated) {
		this.storyLastUpdated = storyLastUpdated;
	}
}
