package com.nwm.coauthor.service.model;

public class StoryEntryModel extends BaseModel{
	private String fbId;
	private String entry;
	
	public StoryEntryModel(){
		
	}
	
	public StoryEntryModel(String fbId, String entry){
		this.fbId = fbId;
		this.entry = entry;
	}
	
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
