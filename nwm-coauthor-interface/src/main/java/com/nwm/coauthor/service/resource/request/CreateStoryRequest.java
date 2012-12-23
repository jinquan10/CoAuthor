package com.nwm.coauthor.service.resource.request;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class CreateStoryRequest extends BaseResource{
	private String title;
	private String entry;
	private Integer numCharacters;
	private List<String> fbFriends;
	
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
