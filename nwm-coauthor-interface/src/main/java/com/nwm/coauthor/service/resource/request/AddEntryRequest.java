package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.resource.BaseResource;

public class AddEntryRequest extends BaseResource{
	private String storyId;
	private String entry;
	private Integer version;
	
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}
