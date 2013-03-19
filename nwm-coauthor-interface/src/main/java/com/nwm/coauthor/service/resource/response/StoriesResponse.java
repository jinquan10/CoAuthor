package com.nwm.coauthor.service.resource.response;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class StoriesResponse extends BaseResource{
	private List<StoryResponse> myStories;

	public static StoriesResponse wrapStoryCovers(List<StoryResponse> stories){
		StoriesResponse response =  new StoriesResponse();
		
		response.setMyStories(stories);
		
		return response;
	}
	
	public List<StoryResponse> getMyStories() {
		return myStories;
	}

	public void setMyStories(List<StoryResponse> myStories) {
		this.myStories = myStories;
	}
}
