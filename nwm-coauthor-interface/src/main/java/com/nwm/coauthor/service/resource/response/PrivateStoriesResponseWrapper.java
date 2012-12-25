package com.nwm.coauthor.service.resource.response;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class PrivateStoriesResponseWrapper extends BaseResource{
	private List<PrivateStoryResponse> stories;

	public PrivateStoriesResponseWrapper(){
		
	}
	
	public PrivateStoriesResponseWrapper(List<PrivateStoryResponse> stories){
		this.stories = stories;
	}
	
	public List<PrivateStoryResponse> getStories() {
		return stories;
	}

	public void setStories(List<PrivateStoryResponse> stories) {
		this.stories = stories;
	}
}
