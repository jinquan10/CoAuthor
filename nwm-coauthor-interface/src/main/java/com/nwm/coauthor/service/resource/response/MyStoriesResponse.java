package com.nwm.coauthor.service.resource.response;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class MyStoriesResponse extends BaseResource{
	private List<StoryCoverResponse> myStories;

	public static MyStoriesResponse wrapStoryCovers(List<StoryCoverResponse> stories){
		MyStoriesResponse response =  new MyStoriesResponse();
		
		response.setMyStories(stories);
		
		return response;
	}
	
	public List<StoryCoverResponse> getMyStories() {
		return myStories;
	}

	public void setMyStories(List<StoryCoverResponse> myStories) {
		this.myStories = myStories;
	}
}
