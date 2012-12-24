package com.nwm.coauthor.service.resource;

public class StoryEntryResource extends BaseResource{
	private String fbId;
	private String entry;
	
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
}
