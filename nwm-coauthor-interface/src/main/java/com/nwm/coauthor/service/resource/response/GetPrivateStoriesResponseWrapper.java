package com.nwm.coauthor.service.resource.response;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class GetPrivateStoriesResponseWrapper extends BaseResource{
	private List<GetPrivateStoryResponse> stories;

	public GetPrivateStoriesResponseWrapper(){
		
	}
	
	public GetPrivateStoriesResponseWrapper(List<GetPrivateStoryResponse> stories){
		this.stories = stories;
	}
	
	public List<GetPrivateStoryResponse> getStories() {
		return stories;
	}

	public void setStories(List<GetPrivateStoryResponse> stories) {
		this.stories = stories;
	}
}
