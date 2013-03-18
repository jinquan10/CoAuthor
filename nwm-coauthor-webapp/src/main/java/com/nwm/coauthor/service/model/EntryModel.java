package com.nwm.coauthor.service.model;

import org.bson.types.ObjectId;

public class EntryModel extends BaseModel {
    private ObjectId storyId;
    private String fbId;
    private String entry;
    private long ordinal;
    
    public static EntryModel newEntryModel(ObjectId storyId, String fbId, String entry, long ordinal){
    	EntryModel newEntryModel = new EntryModel();
    	
    	newEntryModel.setStoryId(storyId);
    	newEntryModel.setFbId(fbId);
    	newEntryModel.setEntry(entry);
    	newEntryModel.setOrdinal(ordinal);
    	
    	return newEntryModel;
    }
    
	public ObjectId getStoryId() {
		return storyId;
	}
	public void setStoryId(ObjectId storyId) {
		this.storyId = storyId;
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
	public long getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(long ordinal) {
		this.ordinal = ordinal;
	}
}
