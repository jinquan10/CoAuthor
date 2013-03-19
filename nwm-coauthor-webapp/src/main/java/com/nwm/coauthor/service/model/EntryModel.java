package com.nwm.coauthor.service.model;


public class EntryModel extends BaseModel {
    private String storyId;
    private String fbId;
    private String entry;
    private Integer ordinal;
    
    public static EntryModel newEntryModel(String storyId, String fbId, String entry, Integer ordinal){
    	EntryModel newEntryModel = new EntryModel();
    	
    	newEntryModel.setStoryId(storyId);
    	newEntryModel.setFbId(fbId);
    	newEntryModel.setEntry(entry);
    	newEntryModel.setOrdinal(ordinal);
    	
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
	public Integer getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
}
