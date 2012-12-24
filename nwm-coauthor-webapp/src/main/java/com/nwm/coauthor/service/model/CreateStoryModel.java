package com.nwm.coauthor.service.model;

import java.util.List;

public class CreateStoryModel {
	private String userId;
	private String title;
	private String entry;
	private Integer numCharacters;
	private List<String> fbFriends;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public Integer getNumCharacters() {
		return numCharacters;
	}
	public void setNumCharacters(Integer numCharacters) {
		this.numCharacters = numCharacters;
	}
	public List<String> getFbFriends() {
		return fbFriends;
	}
	public void setFbFriends(List<String> fbFriends) {
		this.fbFriends = fbFriends;
	}
}
