package com.nwm.coauthor.service.model;

public class EntryModel extends BaseModel {
    private String storyId;
    private String fbId;
    private String entry;
    private Integer currCharCount;
    
    private EntryModel newEntryModel(String storyId, String fbId, String entry, Integer currCharCount){
    	setStoryId(storyId);
    	setFbId(fbId);
    	setEntry(entry);
    	setCurrCharCount(currCharCount);
    	
    	return this;
    }
    
    public static EntryModel newEntryModel(UpdateStoryForNewEntryModel model){
        EntryModel entryModel = new EntryModel();
        entryModel.newEntryModel(model.getStoryId(), model.getLastFriendWithEntry(), model.getLastEntry(), model.getCurrEntryCharCount());
        
        return entryModel;
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
