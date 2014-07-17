package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.resource.BaseResource;

public class NewStory extends BaseResource{
	private String title;
	private String entry;
	
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
}
