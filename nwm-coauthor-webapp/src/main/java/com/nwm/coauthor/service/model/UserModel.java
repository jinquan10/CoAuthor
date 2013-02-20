package com.nwm.coauthor.service.model;

import java.util.List;

public class UserModel{
	private String fbId;
	private String coToken;
	private List<String> storyLikes;
	
	public UserModel(){
		
	}
	
	public UserModel(String fbId, String coToken){
		this.fbId = fbId;
		this.coToken = coToken;
		this.storyLikes = null;
	}
	
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getCoToken() {
		return coToken;
	}
	public void setCoToken(String coToken) {
		this.coToken = coToken;
	}
	
	@Override
	public String toString(){
		StringBuilder storyLikesStringBuilder = new StringBuilder();
		
		for(String storyId : storyLikes){
			storyLikesStringBuilder.append(storyId);
		}
		
		return "fbId: " + fbId + " coToken: " + coToken + " storyLikes: " + storyLikesStringBuilder.toString();
	}

	public List<String> getStoryLikes() {
		return storyLikes;
	}

	public void setStoryLikes(List<String> storyLikes) {
		this.storyLikes = storyLikes;
	}
}
