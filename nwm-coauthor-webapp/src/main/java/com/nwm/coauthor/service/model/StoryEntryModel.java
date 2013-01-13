package com.nwm.coauthor.service.model;

import java.util.UUID;

public class StoryEntryModel extends BaseModel{
	private String entryId;
	private String fbId;
	private String entry;
	
	public StoryEntryModel(){
		
	}
	
	public StoryEntryModel(String fbId, String entry){
		this.entryId = UUID.randomUUID().toString();
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

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
}
