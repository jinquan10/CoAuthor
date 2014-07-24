package com.nwm.coauthor.service.model;


public class EntryModel extends BaseModel {
    private String entry;
    private Integer charCount;

    
    public EntryModel (){
    	
    }

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Integer getCharCount() {
		return charCount;
	}

	public void setCharCount(Integer charCount) {
		this.charCount = charCount;
	}
}
