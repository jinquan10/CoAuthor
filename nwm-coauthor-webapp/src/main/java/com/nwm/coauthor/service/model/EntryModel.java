package com.nwm.coauthor.service.model;

public class EntryModel extends BaseModel {
    private String storyId;
    private String fbId;
    private String entry;
    private Integer currCharCount;
    
    public static EntryModel newEntryModel(String storyId, String fbId, String entry, Integer currCharCount){
    	EntryModel newEntryModel = new EntryModel();
    	
    	newEntryModel.setStoryId(storyId);
    	newEntryModel.setFbId(fbId);
    	newEntryModel.setEntry(entry);
    	newEntryModel.setCurrCharCount(currCharCount);
    	
    	return newEntryModel;
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
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	public Integer getCurrCharCount() {
		return currCharCount;
	}
	public void setCurrCharCount(Integer currCharCount) {
		this.currCharCount = currCharCount;
	}
}
