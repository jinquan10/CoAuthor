package com.nwm.coauthor.service.model;

import org.bson.types.ObjectId;

import com.nwm.coauthor.service.resource.request.AddEntryRequest;

public class AddEntryModel {
	private ObjectId storyId;
	private StoryEntryModel entry;
	private Integer version;
	
	public AddEntryModel(){
		
	}
	
	public AddEntryModel(AddEntryRequest request, String fbId){
		this.storyId = new ObjectId(request.getStoryId());
		this.setEntry(new StoryEntryModel(fbId, request.getEntry()));
		this.version = request.getVersion();
	}
	
	public ObjectId getStoryId() {
		return storyId;
	}
	public void setStoryId(ObjectId storyId) {
		this.storyId = storyId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	public StoryEntryModel getEntry() {
		return entry;
	}

	public void setEntry(StoryEntryModel entry) {
		this.entry = entry;
	}
}
